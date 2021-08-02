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
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.cloud.context.AliCloudAuthorizationMode;
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
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.common.collect.Lists;

import hitool.core.io.FilenameUtils;
import hitool.core.lang3.time.DateUtils;
import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.setup.config.AliyunOssProperties;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileMetaDataDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
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
	private IFileMapper fileMapper;
	private OSS ossClient;
	private AliyunOssProperties ossProperties;
	private AliyunOssTemplate ossTemplate;
	
	public AliyunOssFilestoreProvider(IFileMapper fileMapper, 
			OSS ossClient,
			AliyunOssProperties ossProperties, 
			AliyunOssTemplate ossTemplate) {
		this.fileMapper = fileMapper;
		this.ossClient = ossClient;
		this.ossProperties = ossProperties;
		this.ossTemplate = ossTemplate;
	}

	@Override
	public FilestoreChannel getProvider() {
		return FilestoreChannel.OSS_ALIYUN;
	}
	
	@Override
	public FilestoreConfig getConfig() {
		AliyunOssFilestoreConfig config = new AliyunOssFilestoreConfig();
		config.setEndpoint(ossProperties.getBucket());
		if (ossProperties.getAuthorizationMode() == AliCloudAuthorizationMode.STS) {
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
	        	
	            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region id）
	            DefaultProfile.addEndpoint("", "", "Sts", ossProperties.getSts().getEndpoint());
	            // 构造default profile（参数留空，无需添加region id）
	            IClientProfile profile = DefaultProfile.getProfile("", ossProperties.getSts().getAccessKey(), ossProperties.getSts().getSecretKey());
	            // 用profile构造client
	            DefaultAcsClient client = new DefaultAcsClient(profile);
	            final AssumeRoleRequest request = new AssumeRoleRequest();
	            request.setSysMethod(MethodType.POST);
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
	
	protected AliyunOssStorePath storeFile(MultipartFile file, int width, int height) throws IOException {
		
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
    	
        return new AliyunOssStorePath(ossProperties.getBucketName(), builder.toString(), thumPath.toString());
        
	}
	
	@Override
	public FileDTO upload(String uid,  MultipartFile file, int width, int height) throws Exception {
		FileEntity entity = null;
		try {
			
			// 文件存储结果
        	AliyunOssStorePath storePath = this.storeFile(file, width, height);

			// 上传文件
            String uuid = UUID.randomUUID().toString();
			
			
			// 文件存储记录对象
			entity = new FileEntity();

			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			entity.setUuid(uuid);
			entity.setUid(principal.getUserid());
			entity.setName(file.getOriginalFilename());
			entity.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			entity.setStore(FilestoreChannel.OSS_ALIYUN.getKey());
			entity.setGroup1(storePath.getBucket());
			entity.setPath(storePath.getPath());
			entity.setThumb(storePath.getThumb());
			getFileMapper().insert(entity);
			
			// 文件存储信息
			FileDTO attDTO = new FileDTO();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(storePath.getPath());
			attDTO.setUrl(getOssTemplate().getAccsssURL(storePath));
			if(StringUtils.isNotBlank(storePath.getThumb())) {
				attDTO.setThumb(storePath.getThumb());
				attDTO.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
			}
			attDTO.setExt(entity.getExt());
			
			return attDTO;

		} catch (Exception e) {
			try {
				if (entity != null) {
					getOssClient().deleteObject(entity.getGroup1(), entity.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
	}
	
	@Override
	public List<FileDTO> upload(String uid,  MultipartFile[] files, int width, int height) throws Exception {
		List<FileDTO> attList = Lists.newArrayList();
		for (MultipartFile file : files) {
			FileDTO attDTO = this.upload(uid, file, width, height);
			attList.add(attDTO);
		}
		return attList;
	}

	@Override
	public boolean deleteByPath(List<String> paths) throws Exception {
		if(CollectionUtils.isEmpty(paths)) {
			return false;
		}
		// 查询path对象的文件记录
		List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_path", paths));
		return this.deleteFiles(fileList);
	}

	@Override
	public boolean deleteByUuid(List<String> uuids) throws Exception {
		if(CollectionUtils.isEmpty(uuids)) {
			return false;
		}
		// 查询Uid对象的文件记录
		List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_uuid", uuids));
		return this.deleteFiles(fileList);
	}

	private boolean deleteFiles(List<FileEntity> fileList) throws Exception {
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FileEntity entity : fileList) {
			// 删除旧的文件
			getOssClient().deleteObject(entity.getGroup1(), entity.getPath());
			getFileMapper().deleteById(entity.getId());
		}
		return true;
	}

	@Override
	public FileDTO reupload(String uid, String uuid, MultipartFile file, int width, int height) throws Exception {

		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		if(entity == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// 上传新文件
		FileDTO attDTO = this.upload(uid, file, width, height);
		
		// 删除旧的文件
		getOssClient().deleteObject(entity.getGroup1(), entity.getPath());
		getFileMapper().deleteById(entity.getId());

		return attDTO;
	}

	
	@Override
	public List<FileDTO> listByPath(List<String> paths) throws Exception {
		if (CollectionUtils.isEmpty(paths)) {
            return Lists.newArrayList();
        }
		// 查询文件信息
        List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_path", paths));
		if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
		return this.mapperFile(fileList);
	}
	
	@Override
	public List<FileDTO> listByUuid(List<String> uuids) throws Exception {
		if (CollectionUtils.isEmpty(uuids)) {
            return Lists.newArrayList();
        }
		// 查询文件信息
		List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_uuid", uuids));
		if (CollectionUtils.isEmpty(fileList)) {
            return Lists.newArrayList();
        }
		return this.mapperFile(fileList);
	}
	
	private List<FileDTO> mapperFile(List<FileEntity> fileList) throws Exception {
		List<FileDTO> attList = Lists.newArrayList();	
		fileList = fileList.stream().sorted().collect(Collectors.toList());
		// 循环进行对象转换
		for (FileEntity entity : fileList) {

			// 文件存储信息
			FileDTO attDTO = new FileDTO();

			attDTO.setUuid(entity.getUuid());
			attDTO.setName(entity.getName());
			attDTO.setPath(entity.getPath());
			attDTO.setUrl(getOssTemplate().getAccsssURL(entity.getGroup1(), entity.getPath()));
			if(StringUtils.isNoneBlank(entity.getThumb())) {
				attDTO.setThumb(entity.getThumb());
				attDTO.setThumbUrl(getOssTemplate().getAccsssURL(entity.getGroup1(), entity.getThumb()));
			}
			attDTO.setExt(entity.getExt());
			// 文件元数据
			try {
				ObjectMetadata metaData = getOssClient().getObjectMetadata(entity.getGroup1(), entity.getPath());
				if(!Objects.isNull(metaData)) {
					attDTO.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
						FileMetaDataDTO metaDTO = new FileMetaDataDTO();
						metaDTO.setName(m.getKey());
						metaDTO.setValue(m.getValue().toString());
						return metaDTO; 
					}).collect(Collectors.toSet()));
				}
			} catch (Exception e) {
			}

			attList.add(attDTO);

		}

		return attList;
	}
	
	@Override
	public FileDownloadDTO downloadByPath(String path) throws Exception {
		
		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_path", path));
		if(entity == null) {
			throw new BizRuntimeException(path + "指向的文件不存在");
		}
		return this.mapperFile(entity);
		
	}

	@Override
	public FileDownloadDTO downloadByUuid(String uuid) throws Exception {
		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		if(entity == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		return this.mapperFile(entity);
	}
	
	private FileDownloadDTO mapperFile(FileEntity entity) throws Exception {
		// 文件存储信息
		FileDownloadDTO attDTO = new FileDownloadDTO();
		
		attDTO.setUuid(entity.getUuid());
		attDTO.setName(entity.getName());
		attDTO.setPath(entity.getPath());
		attDTO.setUrl(getOssTemplate().getAccsssURL(entity.getGroup1(), entity.getPath()));
		
		// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		OSSObject ossObject = getOssClient().getObject(new GetObjectRequest(entity.getGroup1(), entity.getPath()).
                <GetObjectRequest>withProgressListener(progressListener));
		
		// 文件元数据
		try {
			ObjectMetadata metaData = ossObject.getObjectMetadata();
			if(!Objects.isNull(metaData)) {
				attDTO.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
					FileMetaDataDTO metaDTO = new FileMetaDataDTO();
					metaDTO.setName(m.getKey());
					metaDTO.setValue(m.getValue().toString());
					return metaDTO; 
				}).collect(Collectors.toSet()));
			}
		} catch (Exception e) {
		}
		
		// 读取文件内容
		attDTO.setBytes(IOUtils.readStreamAsByteArray(ossObject.getObjectContent()));
		  
		return attDTO;
	}
	
	public IFileMapper getFileMapper() {
		return fileMapper;
	}
	
	public OSS getOssClient() {
		return ossClient;
	}
	
	public AliyunOssTemplate getOssTemplate() {
		return ossTemplate;
	}
	
}
