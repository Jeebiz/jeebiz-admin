/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.biz.utils.FilemimeUtils;
import org.springframework.biz.utils.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import io.minio.MinioClient;
import io.minio.ServerSideEncryption;
import io.minio.spring.boot.MinioProperties;
import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.setup.Constants;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CollectionUtils;

/*
 * https://blog.csdn.net/justlpf/article/details/87857254
 */
public class MinioFilestoreProvider implements FilestoreProvider {
	
	private static Logger LOG = LoggerFactory.getLogger(MinioFilestoreProvider.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private IFileMapper fileMapper;
	private MinioClient minioClient;
	private MinioProperties minioProperties;

	public MinioFilestoreProvider(IFileMapper fileMapper, MinioClient minioClient, MinioProperties minioProperties) {
		this.fileMapper = fileMapper;
		this.minioClient = minioClient;
		this.minioProperties = minioProperties;
	}

	@Override
	public FilestoreEnum getProvider() {
		return FilestoreEnum.OSS_MINIO;
	}
	

	@Override
	public FilestoreConfig getConfig() {
		FilestoreConfig config = new FilestoreConfig();
		config.setEndpoint(minioProperties.getEndpoint());
		return config;
	}
	
	@Override
	public FileDTO upload(MultipartFile file, int width, int height) throws Exception {
		
		FileDTO attDTO = null;
		
		try {
			
			// 检查存储桶是否已经存在
            if(minioClient.bucketExists(Constants.GROUP_name)) {
            	LOG.info("Bucket already exists.");
            } else {
                // 创建一个名为ota的存储桶
                minioClient.makeBucket(Constants.GROUP_name);
                LOG.info("create a new bucket.");
            }
			
			String uuid = UUID.randomUUID().toString();
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			String objectName = sdf.format(new Date()) + "/" + uuid + "." + ext;
			
            Map<String, String> headerMap = new HashMap<>(); 
            
            ServerSideEncryption sse = ServerSideEncryption.atRest();
            String contentType = FilemimeUtils.getFileMimeType(file.getOriginalFilename());
            
            // 上传文件
			minioClient.putObject(Constants.GROUP_name, objectName, file.getInputStream(), file.getSize(), headerMap, sse, contentType);
			
			String storePath = minioClient.getObjectUrl(Constants.GROUP_name, objectName);

			// 文件存储记录对象
			FileEntity entity = new FileEntity();
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			entity.setUuid(uuid);
			entity.setUid(principal.getUserid());
			entity.setName(file.getOriginalFilename());
			entity.setExt(ext);
			entity.setTo(FilestoreEnum.OSS_MINIO.getKey());
			entity.setGroup(Constants.GROUP_name);
			entity.setPath(objectName);
			getFileMapper().insert(entity);

			// 文件存储信息
			attDTO = new FileDTO();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(storePath);
				
			return attDTO;
			
		} catch (Exception e) {
			try {
				if(attDTO != null) {
					getMinioClient().removeObject(Constants.GROUP_name, attDTO.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
	}
	
	@Override
	public List<FileDTO> upload(MultipartFile[] files, int width, int height) throws Exception {
		List<FileDTO> attList = Lists.newArrayList();
		for (MultipartFile file : files) {
			FileDTO attDTO = this.upload(file, width, height);
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
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FileEntity entity : fileList) {
			// 删除旧的文件
			getMinioClient().removeObject(entity.getGroup(), entity.getPath());
			getFileMapper().delete(new QueryWrapper<FileEntity>().eq("f_uuid", entity.getUuid()));
		}
		
		return true;
	}
	
	@Override
	public boolean deleteByUuid(List<String> uuids) throws Exception {
		
		// 查询Uid对象的文件记录
        List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_uuid", uuids));
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FileEntity entity : fileList) {
			// 删除旧的文件
			getMinioClient().removeObject(entity.getGroup(), entity.getPath());
			getFileMapper().delete(new QueryWrapper<FileEntity>().eq("f_uuid", entity.getUuid()));
		}
		
		return true;
	}


	@Override
	public FileDTO reupload(String uuid, MultipartFile file, int width, int height) throws Exception {
		
		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		if(entity == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		String objectName = sdf.format(new Date()) + "/" + uuid + "." + ext;
		
        Map<String, String> headerMap = new HashMap<>(); 
        
        ServerSideEncryption sse = ServerSideEncryption.atRest();
        String contentType = FilemimeUtils.getFileMimeType(file.getOriginalFilename());
        
        // 上传新文件	
        getMinioClient().putObject(entity.getGroup(), objectName, file.getInputStream(), file.getSize(), headerMap, sse, contentType);
		String storePath = getMinioClient().getObjectUrl(Constants.GROUP_name, objectName);
		
		// 文件存储信息
		String uuid1 = UUID.randomUUID().toString();
		FileDTO attDTO = new FileDTO();
		attDTO.setUuid(uuid1);
		attDTO.setName(file.getOriginalFilename());
		attDTO.setPath(storePath);
		
		// 文件存储记录对象
		entity.setUid(uuid1);
		entity.setName(file.getOriginalFilename());
		entity.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
		entity.setTo(FilestoreEnum.OSS_MINIO.getKey());
		entity.setGroup(entity.getGroup());
		entity.setPath(storePath);
		getFileMapper().insert(entity);
		
		// 删除旧的文件
		getMinioClient().removeObject(entity.getGroup(), entity.getPath());
		getFileMapper().delete(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		
		return attDTO;
	}
	
	@Override
	public List<FileDTO> listByPath(List<String> paths) throws Exception {
		
		List<FileDTO> attList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(paths)) {
            return attList;
        }
		// 查询文件信息
        List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_path", paths));
		if (CollectionUtils.isEmpty(fileList)) {
            return attList;
        }
		fileList = fileList.stream().sorted().collect(Collectors.toList());
		// 循环进行对象转换
		for (FileEntity entity : fileList) {

			// 文件存储信息
			FileDTO attDTO = new FileDTO();
			
			attDTO.setUuid(entity.getUuid());
			attDTO.setName(entity.getName());
			attDTO.setPath(entity.getPath());
			attDTO.setUrl(getMinioClient().getObjectUrl(entity.getGroup(), entity.getPath()));
			if(StringUtils.isNoneBlank(entity.getThumb())) {
				attDTO.setThumb(entity.getThumb());
				attDTO.setThumbUrl(getMinioClient().getObjectUrl(entity.getGroup(), entity.getThumb()));
			}
			attDTO.setExt(entity.getExt());

			attList.add(attDTO);

		}

		return attList;
		
	}

	@Override
	public List<FileDTO> listByUuid(List<String> uuids) throws Exception {
		
		List<FileDTO> attList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(uuids)) {
            return attList;
        }
		// 查询文件信息
        List<FileEntity> fileList = getFileMapper().selectList(new QueryWrapper<FileEntity>().in("f_uuid", uuids));
		if (CollectionUtils.isEmpty(fileList)) {
            return attList;
        }
		fileList = fileList.stream().sorted().collect(Collectors.toList());
		// 循环进行对象转换
		for (FileEntity entity : fileList) {

			// 文件存储信息
			FileDTO attDTO = new FileDTO();
			
			attDTO.setUuid(entity.getUuid());
			attDTO.setName(entity.getName());
			attDTO.setPath(entity.getPath());
			attDTO.setUrl(getMinioClient().getObjectUrl(entity.getGroup(), entity.getPath()));
			if(StringUtils.isNoneBlank(entity.getThumb())) {
				attDTO.setThumb(entity.getThumb());
				attDTO.setThumbUrl(getMinioClient().getObjectUrl(entity.getGroup(), entity.getThumb()));
			}
			attDTO.setExt(entity.getExt());

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
		
		// 文件存储信息
		FileDownloadDTO attDTO = new FileDownloadDTO();
		
		attDTO.setUuid(entity.getUuid());
		attDTO.setName(entity.getName());
		attDTO.setPath(entity.getPath());
		
		InputStream stream = getMinioClient().getObject(entity.getGroup(), entity.getPath());
		attDTO.setStream(stream);
		
		return attDTO;
	}

	@Override
	public FileDownloadDTO downloadByUuid(String uuid) throws Exception {
		
		// 查询文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		if(entity == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// 文件存储信息
		FileDownloadDTO attDTO = new FileDownloadDTO();
		
		attDTO.setUuid(entity.getUuid());
		attDTO.setName(entity.getName());
		attDTO.setPath(entity.getPath());
		
		InputStream stream = getMinioClient().getObject(entity.getGroup(), entity.getPath());
		attDTO.setStream(stream);
		
		return attDTO;
	}
	
	public IFileMapper getFileMapper() {
		return fileMapper;
	}

	public MinioClient getMinioClient() {
		return minioClient;
	}


}
