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

import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.biz.utils.FilemimeUtils;
import org.springframework.biz.utils.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import io.minio.MinioClient;
import io.minio.ServerSideEncryption;
import io.minio.spring.boot.MinioProperties;
import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.setup.Constants;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDownloadDTO;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CollectionUtils;

/*
 * https://blog.csdn.net/justlpf/article/details/87857254
 */
public class MinioFilestoreProvider implements FilestoreProvider {
	
	private static Logger LOG = LoggerFactory.getLogger(MinioFilestoreProvider.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private IFilestoreDao filestoreDao;
	private MinioClient minioClient;
	private MinioProperties minioProperties;

	public MinioFilestoreProvider(IFilestoreDao filestoreDao, MinioClient minioClient, MinioProperties minioProperties) {
		this.filestoreDao = filestoreDao;
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
	public FilestoreDTO upload(MultipartFile file, int width, int height) throws Exception {
		
		FilestoreDTO attDTO = null;
		
		try {
			
			// 检查存储桶是否已经存在
            if(minioClient.bucketExists(Constants.GROUP_NAME)) {
            	LOG.info("Bucket already exists.");
            } else {
                // 创建一个名为ota的存储桶
                minioClient.makeBucket(Constants.GROUP_NAME);
                LOG.info("create a new bucket.");
            }
			
			String uuid = UUID.randomUUID().toString();
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			String objectName = sdf.format(new Date()) + "/" + uuid + "." + ext;
			
            Map<String, String> headerMap = new HashMap<>(); 
            
            ServerSideEncryption sse = ServerSideEncryption.atRest();
            String contentType = FilemimeUtils.getFileMimeType(file.getOriginalFilename());
            
            // 上传文件
			minioClient.putObject(Constants.GROUP_NAME, objectName, file.getInputStream(), file.getSize(), headerMap, sse, contentType);
			
			String storePath = minioClient.getObjectUrl(Constants.GROUP_NAME, objectName);

			// 文件存储记录对象
			FilestoreModel model = new FilestoreModel();
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			model.setUuid(uuid);
			model.setUid(principal.getUserkey());
			model.setName(file.getOriginalFilename());
			model.setExt(ext);
			model.setTo(FilestoreEnum.OSS_MINIO.getKey());
			model.setGroup(Constants.GROUP_NAME);
			model.setPath(objectName);
			getFilestoreDao().insert(model);

			// 文件存储信息
			attDTO = new FilestoreDTO();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(storePath);
				
			return attDTO;
			
		} catch (Exception e) {
			try {
				if(attDTO != null) {
					getMinioClient().removeObject(Constants.GROUP_NAME, attDTO.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
	}
	
	@Override
	public List<FilestoreDTO> upload(MultipartFile[] files, int width, int height) throws Exception {
		
		List<FilestoreDTO> attList = Lists.newArrayList();
		try {
			
			// 检查存储桶是否已经存在
            if(minioClient.bucketExists(Constants.GROUP_NAME)) {
            	LOG.info("Bucket already exists.");
            } else {
                // 创建一个名为ota的存储桶
                minioClient.makeBucket(Constants.GROUP_NAME);
                LOG.info("create a new bucket.");
            }
			
            ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			for (MultipartFile file : files) {

				String uuid = UUID.randomUUID().toString();
				String ext = FilenameUtils.getExtension(file.getOriginalFilename());
				String objectName = sdf.format(new Date()) + "/" + uuid + "." + ext;
				
                Map<String, String> headerMap = new HashMap<>(); 
                
                ServerSideEncryption sse = ServerSideEncryption.atRest();
                String contentType = FilemimeUtils.getFileMimeType(file.getOriginalFilename());
                
                // 上传文件
				minioClient.putObject(Constants.GROUP_NAME, objectName, file.getInputStream(), file.getSize(), headerMap, sse, contentType);
				
				String storePath = minioClient.getObjectUrl(Constants.GROUP_NAME, objectName);

				// 文件存储记录对象
				FilestoreModel model = new FilestoreModel();
				
				model.setUuid(uuid);
				model.setUid(principal.getUserkey());
				model.setName(file.getOriginalFilename());
				model.setExt(ext);
				model.setTo(FilestoreEnum.OSS_MINIO.getKey());
				model.setGroup(Constants.GROUP_NAME);
				model.setPath(objectName);
				getFilestoreDao().insert(model);

				// 文件存储信息
				FilestoreDTO attDTO = new FilestoreDTO();
				attDTO.setUuid(uuid);
				attDTO.setName(file.getOriginalFilename());
				attDTO.setPath(storePath);
				
				attList.add(attDTO);

			} 
		} catch (Exception e) {
			try {
				for (FilestoreDTO attDTO : attList) {
					getMinioClient().removeObject(Constants.GROUP_NAME, attDTO.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
		return attList;
	}

	@Override
	public boolean deleteByPath(List<String> paths) throws Exception {
		
		if(CollectionUtils.isEmpty(paths)) {
			return false;
		}
		// 查询UID对象的文件记录
		List<FilestoreModel> files = getFilestoreDao().getPaths(paths);
		// 删除文件记录
		getFilestoreDao().deleteByPaths(paths);
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FilestoreModel model : files) {
			// 删除旧的文件
			getMinioClient().removeObject(model.getGroup(), model.getPath());
			getFilestoreDao().delete(model.getUuid());
		}
		
		return true;
	}
	
	@Override
	public boolean deleteByUuid(List<String> uuids) throws Exception {
		
		// 查询UID对象的文件记录
		List<FilestoreModel> files = getFilestoreDao().getFiles(uuids);
		// 删除文件记录
		getFilestoreDao().deleteByUuids(uuids);
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FilestoreModel model : files) {
			// 删除旧的文件
			getMinioClient().removeObject(model.getGroup(), model.getPath());
			getFilestoreDao().delete(model.getUuid());
		}
		
		return true;
	}


	@Override
	public FilestoreDTO reupload(String uuid, MultipartFile file, int width, int height) throws Exception {
		
		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getByUuid(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		
		
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		String objectName = sdf.format(new Date()) + "/" + uuid + "." + ext;
		
        Map<String, String> headerMap = new HashMap<>(); 
        
        ServerSideEncryption sse = ServerSideEncryption.atRest();
        String contentType = FilemimeUtils.getFileMimeType(file.getOriginalFilename());
        
        // 上传新文件	
        getMinioClient().putObject(model.getGroup(), objectName, file.getInputStream(), file.getSize(), headerMap, sse, contentType);
		String storePath = getMinioClient().getObjectUrl(Constants.GROUP_NAME, objectName);
		
		// 文件存储信息
		String uuid1 = UUID.randomUUID().toString();
		FilestoreDTO attDTO = new FilestoreDTO();
		attDTO.setUuid(uuid1);
		attDTO.setName(file.getOriginalFilename());
		attDTO.setPath(storePath);
		
		// 文件存储记录对象
		model.setUid(uuid1);
		model.setName(file.getOriginalFilename());
		model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
		model.setTo(FilestoreEnum.OSS_MINIO.getKey());
		model.setGroup(model.getGroup());
		model.setPath(storePath);
		getFilestoreDao().insert(model);
		
		// 删除旧的文件
		getMinioClient().removeObject(model.getGroup(), model.getPath());
		getFilestoreDao().delete(uuid);
		
		return attDTO;
	}
	
	@Override
	public List<FilestoreDTO> listByPath(List<String> paths) throws Exception {
		
		List<FilestoreDTO> attList = Lists.newArrayList();
		
		for (String path : paths) {
			
			// 文件存储信息
			FilestoreDTO attDTO = new FilestoreDTO();
			
			attDTO.setPath(path);
			String storePath = getMinioClient().getObjectUrl(Constants.GROUP_NAME, path);
			attDTO.setUrl(storePath);
			
			attList.add(attDTO);

		} 
		
		return attList;
	}

	@Override
	public List<FilestoreDTO> listByUuid(List<String> uuids) throws Exception {
		
		List<FilestoreDTO> attList = Lists.newArrayList();
		
		// 查询文件信息
		List<FilestoreModel> fileList = getFilestoreDao().getFiles(uuids);
				
		for (FilestoreModel model : fileList) {
			
			// 文件存储信息
			FilestoreDTO attDTO = new FilestoreDTO();
			
			attDTO.setUuid(model.getUuid());
			attDTO.setName(model.getName());
			attDTO.setPath(model.getPath());
			String storePath = getMinioClient().getObjectUrl(model.getGroup(), model.getPath());
			attDTO.setUrl(storePath);
			
			attList.add(attDTO);

		} 
		
		return attList;
	}
	
	@Override
	public FilestoreDownloadDTO downloadByPath(String path) throws Exception {
		
		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getByPath(path);
		if(model == null) {
			throw new BizRuntimeException(path + "指向的文件不存在");
		}
		
		// 文件存储信息
		FilestoreDownloadDTO attDTO = new FilestoreDownloadDTO();
		
		attDTO.setUuid(model.getUuid());
		attDTO.setName(model.getName());
		attDTO.setPath(model.getPath());
		
		InputStream stream = getMinioClient().getObject(model.getGroup(), model.getPath());
		attDTO.setStream(stream);
		
		return attDTO;
	}

	@Override
	public FilestoreDownloadDTO downloadByUuid(String uuid) throws Exception {
		
		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getByUuid(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// 文件存储信息
		FilestoreDownloadDTO attDTO = new FilestoreDownloadDTO();
		
		attDTO.setUuid(model.getUuid());
		attDTO.setName(model.getName());
		attDTO.setPath(model.getPath());
		
		InputStream stream = getMinioClient().getObject(model.getGroup(), model.getPath());
		attDTO.setStream(stream);
		
		return attDTO;
	}
	
	public IFilestoreDao getFilestoreDao() {
		return filestoreDao;
	}

	public MinioClient getMinioClient() {
		return minioClient;
	}


}
