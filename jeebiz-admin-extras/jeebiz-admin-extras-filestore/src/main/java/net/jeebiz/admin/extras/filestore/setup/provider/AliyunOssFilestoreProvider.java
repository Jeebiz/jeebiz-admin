/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.biz.utils.DateUtils;
import org.springframework.biz.utils.FilenameUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import com.aliyun.oss.spring.boot.OssAuthorizationMode;
import com.aliyun.oss.spring.boot.OssProperties;
import com.aliyun.oss.spring.boot.OssStorePath;
import com.aliyun.oss.spring.boot.OssTemplate;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.setup.config.AliyunOssFilestoreConfig;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.admin.extras.filestore.web.vo.FileMetaDataVo;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreDownloadVo;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreVo;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.api.utils.CalendarUtils;
import net.jeebiz.boot.api.utils.CollectionUtils;
public class AliyunOssFilestoreProvider implements FilestoreProvider {

	private static final String FOLDER_SEPARATOR = "/";
	private static final String ORIGINAL_FILE_NAME = "Original-File-Name";
	private static final String X_OSS_PROCESS = "?x-oss-process=image/resize,m_fill,h_%d,w_%d,limit_0";
	
	private GetObjectProgressListener progressListener = new GetObjectProgressListener();
	private Sequence randomString = new Sequence(0);
	private IFilestoreDao filestoreDao;
	private OSS ossClient;
	private OssProperties ossProperties;
	private OssTemplate ossTemplate;
	
	public AliyunOssFilestoreProvider(IFilestoreDao filestoreDao, 
			OSS ossClient,
			OssProperties ossProperties, 
			OssTemplate ossTemplate) {
		this.filestoreDao = filestoreDao;
		this.ossClient = ossClient;
		this.ossProperties = ossProperties;
		this.ossTemplate = ossTemplate;
	}

	@Override
	public FilestoreEnum getProvider() {
		return FilestoreEnum.OSS_ALIYUN;
	}
	
	@Override
	public FilestoreConfig getConfig() {
		AliyunOssFilestoreConfig config = new AliyunOssFilestoreConfig();
		config.setEndpoint(ossProperties.getBucket());
		if (ossProperties.getAuthorizationMode() == OssAuthorizationMode.STS) {
			Assert.isTrue(!StringUtils.isEmpty(ossProperties.getEndpoint()), "Oss endpoint can't be empty.");
			Assert.isTrue(!StringUtils.isEmpty(ossProperties.getSts().getAccessKey()), "Access key can't be empty.");
			Assert.isTrue(!StringUtils.isEmpty(ossProperties.getSts().getSecretKey()), "Secret key can't be empty.");
			Assert.isTrue(!StringUtils.isEmpty(ossProperties.getSts().getSecurityToken()),
					"Security Token can't be empty.");
			
			config.setBucketName(ossProperties.getBucketName());
			config.setAccessKey(ossProperties.getSts().getAccessKey());
			config.setAccessKeySecret(ossProperties.getSts().getSecretKey());
			config.setSecurityToken(ossProperties.getSts().getSecurityToken());
	        
	        try {
	        	
	            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
	            DefaultProfile.addEndpoint("", "", "Sts", ossProperties.getSts().getEndpoint());
	            // 构造default profile（参数留空，无需添加region ID）
	            IClientProfile profile = DefaultProfile.getProfile("", ossProperties.getSts().getAccessKey(), ossProperties.getSts().getSecretKey());
	            // 用profile构造client
	            DefaultAcsClient client = new DefaultAcsClient(profile);
	            final AssumeRoleRequest request = new AssumeRoleRequest();
	            request.setMethod(MethodType.POST);
	            request.setRoleArn(ossProperties.getSts().getRoleArn());
	            request.setRoleSessionName(ossProperties.getSts().getRoleSessionName());
	            //request.setPolicy(policy); // 若policy为空，则用户将获得该角色下所有权限
	            request.setDurationSeconds(CalendarUtils.getSecondsNextEarlyMorning()); // 设置凭证有效时间
	            final AssumeRoleResponse response = client.getAcsResponse(request);
	            System.out.println("Expiration: " + response.getCredentials().getExpiration());
	            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
	            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
	            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
	            System.out.println("RequestId: " + response.getRequestId());
	            
	            config.setAccessKey(response.getCredentials().getAccessKeyId());
				config.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
				config.setExpiration(response.getCredentials().getExpiration());
				config.setSecurityToken(response.getCredentials().getSecurityToken());
				
	        } catch (ClientException e) {
	            System.out.println("Failed：");
	            System.out.println("Error code: " + e.getErrCode());
	            System.out.println("Error message: " + e.getErrMsg());
	            System.out.println("RequestId: " + e.getRequestId());
	        }
		        
		}
		return config;
	}
	
	static class GetObjectProgressListener implements ProgressListener {
		
        private long bytesRead = 0;
        private long totalBytes = -1;
        private boolean succeed = false;
        
        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
	            case TRANSFER_STARTED_EVENT:
	                System.out.println("Start to download......");
	                break;
	            case RESPONSE_CONTENT_LENGTH_EVENT:
	                this.totalBytes = bytes;
	                System.out.println(this.totalBytes + " bytes in total will be downloaded to a local file");
	                break;
	            case RESPONSE_BYTE_TRANSFER_EVENT:
	                this.bytesRead += bytes;
	                if (this.totalBytes != -1) {
	                    int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
	                    System.out.println(bytes + " bytes have been read at this time, download progress: " +
	                            percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
	                } else {
	                    System.out.println(bytes + " bytes have been read at this time, download ratio: unknown" +
	                            "(" + this.bytesRead + "/...)");
	                }
	                break;
	            case TRANSFER_COMPLETED_EVENT:
	                this.succeed = true;
	                System.out.println("Succeed to download, " + this.bytesRead + " bytes have been transferred in total");
	                break;
	            case TRANSFER_FAILED_EVENT:
	                System.out.println("Failed to download, " + this.bytesRead + " bytes have been transferred");
	                break;
	            default:
	            	break;
            }
        }
        
        public boolean isSucceed() {
            return succeed;
        }
    }
	
	protected Map<String, String> metaDataMap(MultipartFile file) {
		Map<String, String> metaDataSet = new HashMap<>();
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());
			for (Directory directory : metadata.getDirectories()) {
			    for (Tag tag : directory.getTags()) {
			        //格式化输出[directory.getName()] - tag.getTagName() = tag.getDescription()
			        System.out.format("[%s] - %s = %s%n",  directory.getName(), tag.getTagName(), tag.getDescription());
			        metaDataSet.put(directory.getName(), tag.getTagName());
			    }
			    if (directory.hasErrors()) {
			        for (String error : directory.getErrors()) {
			            System.err.format("ERROR: %s", error);
			        }
			    }
			}
		} catch (Exception e) {
		}
		return metaDataSet;
	}
	
	protected OssStorePath storeFile(MultipartFile file, int width, int height) throws IOException {
		
		// 文件元数据与访问权限
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());
		metadata.setObjectAcl(CannedAccessControlList.PublicRead);
		
    	// 上传并且生成缩略图
        StringBuilder builder = new StringBuilder();
        	builder.append(DateUtils.getDate());
	        builder.append(FOLDER_SEPARATOR);
	        builder.append(randomString.nextId().toString());
	        builder.append(FilenameUtils.getFullExtension(file.getOriginalFilename()));
	        
        StringBuilder thumPath = new StringBuilder();
        // 上传的是图片且可生成缩略图的图片
        if(FilestoreUtils.isImage(file) && width > 0 && height > 0 && FilestoreUtils.thumbable(file)) {
        	// oss 通过 ?x-oss-process=image/resize,w_300,m_lfit 设置缩略图
        	thumPath.append(builder.toString()).append(String.format(X_OSS_PROCESS, height, width));
		}
	        
    	// 创建PutObjectRequest对象。
		PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), builder.toString(), file.getInputStream()).
                <PutObjectRequest>withProgressListener(progressListener);
		
		// 设置元信息
		Map<String, String> userMetadata = new HashMap<>();
		userMetadata.put(ORIGINAL_FILE_NAME, file.getOriginalFilename());
		metadata.setUserMetadata(userMetadata);
		putObjectRequest.setMetadata(metadata);
		
		// 上传文件
    	getOssClient().putObject(putObjectRequest);
    	
        return new OssStorePath(ossProperties.getBucketName(), builder.toString(), thumPath.toString());
        
	}
	
	@Override
	public FilestoreVo upload(MultipartFile file, int width, int height) throws Exception {
		FilestoreModel model = null;
		try {
			
			// 文件存储结果
        	OssStorePath storePath = this.storeFile(file, width, height);

			// 上传文件
            String uuid = UUID.randomUUID().toString();
			
			
			// 文件存储记录对象
			model = new FilestoreModel();

			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			model.setUuid(uuid);
			model.setUid(principal.getUserkey());
			model.setName(file.getOriginalFilename());
			model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			model.setTo(FilestoreEnum.OSS_ALIYUN.getKey());
			model.setGroup(storePath.getBucket());
			model.setPath(storePath.getPath());
			model.setThumb(storePath.getThumb());
			getFilestoreDao().insert(model);
			
			// 文件存储信息
			FilestoreVo attVo = new FilestoreVo();
			attVo.setUuid(uuid);
			attVo.setName(file.getOriginalFilename());
			attVo.setPath(storePath.getPath());
			attVo.setUrl(getOssTemplate().getAccsssURL(storePath));
			if(StringUtils.isNotBlank(storePath.getThumb())) {
				attVo.setThumb(storePath.getThumb());
				attVo.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
			}
			attVo.setExt(model.getExt());
			
			return attVo;

		} catch (Exception e) {
			try {
				if (model != null) {
					getOssClient().deleteObject(model.getGroup(), model.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
	}
	
	@Override
	public List<FilestoreVo> upload(MultipartFile[] files, int width, int height) throws Exception {

		List<FilestoreModel> modelList = Lists.newArrayList();
		List<FilestoreVo> attList = Lists.newArrayList();
		try {
			
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			for (MultipartFile file : files) {

				// 文件存储结果
				OssStorePath storePath = this.storeFile(file, width, height);
				
				// 文件存储记录对象
				FilestoreModel model = new FilestoreModel();
				
				String uuid = UUID.randomUUID().toString();
				model.setUuid(uuid);
				model.setUid(principal.getUserkey());
				model.setName(file.getOriginalFilename());
				model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
				model.setTo(FilestoreEnum.OSS_ALIYUN.getKey());
				model.setGroup(storePath.getBucket());
				model.setPath(storePath.getPath());
				model.setThumb(storePath.getThumb());
				getFilestoreDao().insert(model);
				modelList.add(model);

				// 文件存储信息
				FilestoreVo attVo = new FilestoreVo();
				attVo.setUuid(uuid);
				attVo.setName(file.getOriginalFilename());
				attVo.setPath(storePath.getPath());
				attVo.setUrl(getOssTemplate().getAccsssURL(storePath));
				if(StringUtils.isNotBlank(storePath.getThumb())) {
					attVo.setThumb(storePath.getThumb());
					attVo.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
				}
				attVo.setExt(model.getExt());

				attList.add(attVo);

			}
		} catch (IOException e) {
			try {
				for (FilestoreModel model : modelList) {
					getOssClient().deleteObject(model.getGroup(), model.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
		}
		return attList;
	}

	@Override
	public boolean deleteByPath(List<String> paths) throws Exception {

		if(CollectionUtils.isEmpty(paths)) {
			return false;
		}
		// 查询文件信息
		List<FilestoreModel> files = getFilestoreDao().getPaths(paths);
		// 删除文件记录
		getFilestoreDao().deleteByPaths(paths);
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FilestoreModel model : files) {
			// 删除旧的文件
			getOssClient().deleteObject(model.getGroup(), model.getPath());
			getFilestoreDao().delete(model.getUuid());
		}

		return true;
	}

	@Override
	public boolean deleteByUuid(List<String> uuids) throws Exception {

		if(CollectionUtils.isEmpty(uuids)) {
			return false;
		}
		// 查询UID对象的文件记录
		List<FilestoreModel> files = getFilestoreDao().getFiles(uuids);
		// 删除文件记录
		getFilestoreDao().deleteByUuids(uuids);
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FilestoreModel model : files) {
			// 删除旧的文件
			getOssClient().deleteObject(model.getGroup(), model.getPath());
			getFilestoreDao().delete(model.getUuid());
		}

		return true;
	}


	@Override
	public FilestoreVo reupload(String uuid, MultipartFile file, int width, int height) throws Exception {

		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getModel(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		
        // 文件存储结果
    	OssStorePath storePath = this.storeFile(file, width, height);
		
		// 文件存储信息

        String uuid1 = UUID.randomUUID().toString();
		
        FilestoreVo attVo = new FilestoreVo();
		attVo.setUuid(uuid1);
		attVo.setName(file.getOriginalFilename());
		attVo.setPath(storePath.getPath());
		attVo.setUrl(getOssTemplate().getAccsssURL(storePath));
		if(StringUtils.isNotBlank(storePath.getThumb())) {
			attVo.setThumb(storePath.getThumb());
			attVo.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
		}
		
		// 文件存储记录对象
		model.setUuid(uuid1);
		model.setUid(principal.getUserkey());
		model.setName(file.getOriginalFilename());
		model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
		model.setTo(FilestoreEnum.OSS_ALIYUN.getKey());
		model.setGroup(storePath.getBucket());
		model.setPath(storePath.getPath());
		model.setThumb(storePath.getThumb());
		getFilestoreDao().insert(model);

		// 删除旧的文件
		getOssClient().deleteObject(model.getGroup(), model.getPath());
		getFilestoreDao().delete(uuid);

		attVo.setExt(model.getExt());
		return attVo;
	}

	
	@Override
	public List<FilestoreVo> listByPath(List<String> paths) throws Exception {

		List<FilestoreVo> attList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(paths)) {
            return attList;
        }
		// 查询文件信息
		List<FilestoreModel> fileList = getFilestoreDao().getPaths(paths);
		if (CollectionUtils.isEmpty(fileList)) {
            return attList;
        }
		// 根据传入路径顺序进行排序
		for (FilestoreModel model : fileList) {
			for (int i = 0; i < paths.size(); i++) {
				if(StringUtils.equalsIgnoreCase(model.getPath(), paths.get(i))) {
					model.setOrder(i);
				}
			}
		}
		fileList = fileList.stream().sorted().collect(Collectors.toList());
		// 循环进行对象转换
		for (FilestoreModel model : fileList) {

			// 文件存储信息
			FilestoreVo attVo = new FilestoreVo();

			attVo.setUuid(model.getUuid());
			attVo.setName(model.getName());
			attVo.setPath(model.getPath());
			attVo.setUrl(getOssTemplate().getAccsssURL(model.getGroup(), model.getPath()));
			if(StringUtils.isNoneBlank(model.getThumb())) {
				attVo.setThumb(model.getThumb());
				attVo.setThumbUrl(getOssTemplate().getAccsssURL(model.getGroup(), model.getThumb()));
			}
			attVo.setExt(model.getExt());
			// 文件元数据
			try {
				ObjectMetadata metaData = getOssClient().getObjectMetadata(model.getGroup(), model.getPath());
				if(!Objects.isNull(metaData)) {
					attVo.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
						FileMetaDataVo metaVo = new FileMetaDataVo();
						metaVo.setName(m.getKey());
						metaVo.setValue(m.getValue().toString());
						return metaVo; 
					}).collect(Collectors.toSet()));
				}
			} catch (Exception e) {
			}

			attList.add(attVo);

		}

		return attList;
	}
	
	@Override
	public List<FilestoreVo> listByUuid(List<String> uuids) throws Exception {

		List<FilestoreVo> attList = Lists.newArrayList();

		// 查询文件信息
		List<FilestoreModel> fileList = getFilestoreDao().getFiles(uuids);
		if (CollectionUtils.isEmpty(fileList)) {
            return attList;
        }
		// 根据传入路径顺序进行排序
		for (FilestoreModel model : fileList) {
			for (int i = 0; i < uuids.size(); i++) {
				if(StringUtils.equalsIgnoreCase(model.getUuid(), uuids.get(i))) {
					model.setOrder(i);
				}
			}
		}
		fileList = fileList.stream().sorted().collect(Collectors.toList());
		// 循环进行对象转换
		for (FilestoreModel model : fileList) {

			// 文件存储信息
			FilestoreVo attVo = new FilestoreVo();

			attVo.setUuid(model.getUuid());
			attVo.setName(model.getName());
			attVo.setPath(model.getPath());
			attVo.setUrl(getOssTemplate().getAccsssURL(model.getGroup(), model.getPath()));
			if(StringUtils.isNoneBlank(model.getThumb())) {
				attVo.setThumb(model.getThumb());
				attVo.setThumbUrl(getOssTemplate().getAccsssURL(model.getGroup(), model.getThumb()));
			}
			attVo.setExt(model.getExt());
			// 文件元数据
			try {
				ObjectMetadata metaData = getOssClient().getObjectMetadata(model.getGroup(), model.getPath());
				if(!Objects.isNull(metaData)) {
					attVo.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
						FileMetaDataVo metaVo = new FileMetaDataVo();
						metaVo.setName(m.getKey());
						metaVo.setValue(m.getValue().toString());
						return metaVo; 
					}).collect(Collectors.toSet()));
				}
			} catch (Exception e) {
			}

			attList.add(attVo);

		}

		return attList;
	}
	
	@Override
	public FilestoreDownloadVo downloadByPath(String path) throws Exception {
		
		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getByPath(path);
		if(model == null) {
			throw new BizRuntimeException(path + "指向的文件不存在");
		}
		
		// 文件存储信息
		FilestoreDownloadVo attVo = new FilestoreDownloadVo();
		
		attVo.setUuid(model.getUuid());
		attVo.setName(model.getName());
		attVo.setPath(model.getPath());
		attVo.setUrl(getOssTemplate().getAccsssURL(model.getGroup(), model.getPath()));
		
		// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		OSSObject ossObject = getOssClient().getObject(new GetObjectRequest(model.getGroup(), model.getPath()).
                <GetObjectRequest>withProgressListener(progressListener));
		
		// 文件元数据
		try {
			ObjectMetadata metaData = ossObject.getObjectMetadata();
			if(!Objects.isNull(metaData)) {
				attVo.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
					FileMetaDataVo metaVo = new FileMetaDataVo();
					metaVo.setName(m.getKey());
					metaVo.setValue(m.getValue().toString());
					return metaVo; 
				}).collect(Collectors.toSet()));
			}
		} catch (Exception e) {
		}
		
		// 读取文件内容
		attVo.setBytes(IOUtils.readStreamAsByteArray(ossObject.getObjectContent()));
		
		return attVo;
		
	}

	@Override
	public FilestoreDownloadVo downloadByUuid(String uuid) throws Exception {
		
		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getByUuid(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// 文件存储信息
		FilestoreDownloadVo attVo = new FilestoreDownloadVo();
		
		attVo.setUuid(model.getUuid());
		attVo.setName(model.getName());
		attVo.setPath(model.getPath());
		attVo.setUrl(getOssTemplate().getAccsssURL(model.getGroup(), model.getPath()));
		
		// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		OSSObject ossObject = getOssClient().getObject(new GetObjectRequest(model.getGroup(), model.getPath()).
                <GetObjectRequest>withProgressListener(progressListener));
		
		// 文件元数据
		try {
			ObjectMetadata metaData = ossObject.getObjectMetadata();
			if(!Objects.isNull(metaData)) {
				attVo.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
					FileMetaDataVo metaVo = new FileMetaDataVo();
					metaVo.setName(m.getKey());
					metaVo.setValue(m.getValue().toString());
					return metaVo; 
				}).collect(Collectors.toSet()));
			}
		} catch (Exception e) {
		}
		
		// 读取文件内容
		attVo.setBytes(IOUtils.readStreamAsByteArray(ossObject.getObjectContent()));
		  
		return attVo;
	}
	
	public IFilestoreDao getFilestoreDao() {
		return filestoreDao;
	}
	
	public OSS getOssClient() {
		return ossClient;
	}
	
	public OssTemplate getOssTemplate() {
		return ossTemplate;
	}
	
}
