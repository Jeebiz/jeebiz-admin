/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.strategy;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.cloud.context.AliCloudAuthorizationMode;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.IOUtils;
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
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import hitool.core.io.FilenameUtils;
import hitool.core.lang3.time.DateUtils;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.filestore.bo.FileData;
import net.jeebiz.admin.extras.filestore.bo.FileDownloadResult;
import net.jeebiz.admin.extras.filestore.bo.FileMetaData;
import net.jeebiz.admin.extras.filestore.bo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CalendarUtils;

@Component
@Configuration
@EnableConfigurationProperties({ AliyunOssProperties.class })
@Slf4j
public class AliyunOssFilestoreStrategy extends AbstractFilestoreStrategy {

	private static final String FOLDER_SEPARATOR = "/";
	private static final String ORIGINAL_FILE_NAME = "Original-File-Name";
	private static final String X_OSS_PROCESS = "?x-oss-process=image/resize,m_fill,h_%d,w_%d,limit_0";
	private static final AliyunOssGetObjectProgressListener progressListener = new AliyunOssGetObjectProgressListener();
	
	@Autowired
	private OSS ossClient;
	@Autowired
	private AliyunOssProperties ossProperties;

	@Override
	public FilestoreChannel getChannel() {
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
			Assert.isTrue(!StringUtils.isEmpty(ossProperties.getSts().getSecurityToken()), "Security Token can't be empty.");
			
			config.setBucketName(ossProperties.getBucketName());
			config.setAccessKey(ossProperties.getSts().getAccessKey());
			config.setAccessKeySecret(ossProperties.getSts().getSecretKey());
			config.setSecurityToken(ossProperties.getSts().getSecurityToken());
	        
	        try {
	        	
	        	// STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
	            String endpoint = ossProperties.getSts().getEndpoint();
	            // 填写步骤1生成的访问密钥AccessKey ID和AccessKey Secret。
	            String accessKeyId = ossProperties.getSts().getAccessKey();
	            String accessKeySecret = ossProperties.getSts().getSecretKey();
	            // 填写步骤3获取的角色ARN。
	            String roleArn = ossProperties.getSts().getRoleArn();
	            // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。        
	            String roleSessionName = ossProperties.getSts().getRoleSessionName();
	            // 以下Policy用于限制仅允许使用临时访问凭证向目标存储空间examplebucket上传文件。
	            // 临时访问凭证最后获得的权限是步骤4设置的角色权限和该Policy设置权限的交集，即仅允许将文件上传至目标存储空间examplebucket下的exampledir目录。
	            String policy = "{\n" +
	                    "    \"Version\": \"1\", \n" +
	                    "    \"Statement\": [\n" +
	                    "        {\n" +
	                    "            \"Action\": [\n" +
	                    "                \"oss:PutObject\"\n" +
	                    "                \"oss:GetObject\"\n" +
	                    "                \"oss:DeleteObject\"\n" +
	                    "            ], \n" +
	                    "            \"Resource\": [\n" +
	                    "                \"acs:oss:*:*:" + ossProperties.getBucketName() + "/*\" \n" +
	                    "            ], \n" +
	                    "            \"Effect\": \"Allow\"\n" +
	                    "        }\n" +
	                    "    ]\n" +
	                    "}";
	            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
	            String regionId = "";
	            // 添加endpoint。
	            DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
	            // 构造default profile。
	            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
	            // 构造client。
	            DefaultAcsClient client = new DefaultAcsClient(profile);
	            final AssumeRoleRequest request = new AssumeRoleRequest();
	            //request.setSysEndpoint(kdingProperteis.getOssStsEndpoint());
	            request.setSysMethod(MethodType.POST);
	            request.setRoleArn(roleArn);
	            request.setRoleSessionName(roleSessionName);
	            //request.setPolicy(policy); // 如果policy为空，则用户将获得该角色下所有权限。
	            request.setDurationSeconds(Math.max(3600L, CalendarUtils.getSecondsNextEarlyMorning())); // 设置临时访问凭证的有效时间为3600秒。

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
	            log.error("Sts Failed，Error code: {}，Error message: {}，RequestId: {}", e.getErrCode(), e.getErrMsg(), e.getRequestId());
	            throw new BizRuntimeException("oss config error !");
	        }
		}
		return config;
	}

	@Override
	protected FileData handleFileUpload(MultipartFile file, int width, int height) throws Exception {
		try {
			
			// 文件存储结果
        	AliyunOssStorePath storePath = this.storeFile(file, width, height);
			
			// 文件存储信息
        	String uuid = getSequence().nextId().toString();
			FileData attDTO = new FileData();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(storePath.getPath());
			attDTO.setUrl(this.getAccsssURL(storePath));
			if(StringUtils.isNotBlank(storePath.getThumb())) {
				attDTO.setThumb(storePath.getThumb());
				attDTO.setThumbUrl(this.getThumbAccsssURL(storePath));
			}
			attDTO.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			
			return attDTO;
			
		} catch (Exception e) {
			throw new BizRuntimeException("存储IO异常");
		}
	}

	@Override
	protected boolean handleFileDelete(FileEntity entity) throws Exception {
		// 删除旧的文件
		getOssClient().deleteObject(entity.getGroup1(), entity.getPath());
		return true;
	}

	@Override
	protected FileData handleFileMetadata(FileEntity entity) {
		try {
			// 文件存储信息
			FileData attDTO = new FileData();
	
			attDTO.setUuid(entity.getUuid());
			attDTO.setName(entity.getName());
			attDTO.setPath(entity.getPath());
			attDTO.setUrl(this.getAccsssURL(entity.getGroup1(), entity.getPath()));
			if(StringUtils.isNoneBlank(entity.getThumb())) {
				attDTO.setThumb(entity.getThumb());
				attDTO.setThumbUrl(this.getAccsssURL(entity.getGroup1(), entity.getThumb()));
			}
			attDTO.setExt(entity.getExt());
			// 文件元数据
			try {
				ObjectMetadata metaData = getOssClient().getObjectMetadata(entity.getGroup1(), entity.getPath());
				if(!Objects.isNull(metaData)) {
					attDTO.setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
						FileMetaData metaDTO = new FileMetaData();
						metaDTO.setName(m.getKey());
						metaDTO.setValue(m.getValue().toString());
						return metaDTO; 
					}).collect(Collectors.toSet()));
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void handleFileDownload(FileEntity entity, FileDownloadResult downloadRt) throws Exception {
		
		// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		OSSObject ossObject = getOssClient().getObject(new GetObjectRequest(entity.getGroup1(), entity.getPath()).
                <GetObjectRequest>withProgressListener(progressListener));
		
		// 文件元数据
		try {
			ObjectMetadata metaData = ossObject.getObjectMetadata();
			if(!Objects.isNull(metaData)) {
				downloadRt.getFile().setMetadata(metaData.getRawMetadata().entrySet().stream().map(m -> {
					FileMetaData metaDTO = new FileMetaData();
					metaDTO.setName(m.getKey());
					metaDTO.setValue(m.getValue().toString());
					return metaDTO; 
				}).collect(Collectors.toSet()));
			}
		} catch (Exception e) {
		}
		
		// 读取文件内容
		downloadRt.setBytes(IOUtils.readStreamAsByteArray(ossObject.getObjectContent()));
		
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
	        builder.append(getSequence().nextId().toString());
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
	
	public String getAccsssURL(String bucket, String path) throws Exception {
		// 过期时间为当日23:59:59
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date expiration = new Date(cal.getTimeInMillis());
		return getOssClient().generatePresignedUrl(bucket, path, expiration).toString();
	}
	
	public String getAccsssURL(AliyunOssStorePath storePath) throws Exception {
		return this.getAccsssURL(storePath.getBucket(), storePath.getPath());
	}
	
	public String getThumbAccsssURL(AliyunOssStorePath storePath) throws Exception {
		return this.getAccsssURL(storePath.getBucket(), storePath.getThumb());
	}
	
	public OSS getOssClient() {
		return ossClient;
	}
	
}
