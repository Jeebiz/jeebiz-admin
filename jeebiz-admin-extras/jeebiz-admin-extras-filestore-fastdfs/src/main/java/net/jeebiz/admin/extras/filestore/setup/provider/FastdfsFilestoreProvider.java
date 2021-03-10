/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;


import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.biz.utils.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.spring.boot.FastdfsTemplate;
import com.github.tobato.fastdfs.spring.boot.FileStorePath;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.setup.Constants;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.admin.extras.filestore.web.dto.FileMetaDataDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDownloadDTO;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CollectionUtils;

public class FastdfsFilestoreProvider implements FilestoreProvider {
	
	private DownloadByteArray callback = new DownloadByteArray();
	private IFilestoreDao filestoreDao;
	private FastFileStorageClient fdfsStorageClient;
	private FastdfsTemplate fdfsTemplate;

	public FastdfsFilestoreProvider(IFilestoreDao filestoreDao, 
									FastFileStorageClient fdfsStorageClient,
									FastdfsTemplate fdfsTemplate) {
		this.filestoreDao = filestoreDao;
		this.fdfsStorageClient = fdfsStorageClient;
		this.fdfsTemplate = fdfsTemplate;
	}

	@Override
	public FilestoreEnum getProvider() {
		return FilestoreEnum.FDFS;
	}
	
	@Override
	public FilestoreConfig getConfig() {
		FilestoreConfig config = new FilestoreConfig();
		config.setEndpoint(getFdfsTemplate().getEndpoint());
		return config;
	}
	
	protected Set<MetaData> metaDataSet(MultipartFile file) {
		Set<MetaData> metaDataSet = Sets.newHashSet();
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());
			for (Directory directory : metadata.getDirectories()) {
			    for (Tag tag : directory.getTags()) {
			        //格式化输出[directory.getName()] - tag.getTagName() = tag.getDescription()
			        System.out.format("[%s] - %s = %s%n",  directory.getName(), tag.getTagName(), tag.getDescription());
			        metaDataSet.add(new MetaData(directory.getName(), tag.getTagName()));
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
	
	protected StorePath storeFile(MultipartFile file, int width, int height) throws IOException {
		// 文件存储结果
    	StorePath storePath = null;
        // 上传的是图片
        if(FilestoreUtils.isImage(file)) {
        	
        	// 可生成缩略图的图片
        	if(width > 0 && height > 0 && FilestoreUtils.thumbable(file)) {
		        // 文件后缀
        		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        		// 文件上传信息
		        FastImageFile fastImageFile = new FastImageFile.Builder()
                    .withFile(file.getInputStream(), file.getSize(), extension)
                    .withThumbImage(width, height)
                    .toGroup(Constants.GROUP_NAME)
                    .build();
		        
		        // 上传并且生成缩略图
		        storePath = getFdfsStorageClient().uploadImage(fastImageFile);
		        
		        StringBuilder builder = new StringBuilder();
		        	builder.append(FilenameUtils.getPath(storePath.getPath()));
			        builder.append(FilenameUtils.getBaseName(storePath.getPath()));
			        builder.append(fastImageFile.getThumbImage().getPrefixName());
			        builder.append(".").append(extension);
		        
		        storePath = new FileStorePath(storePath, builder.toString());
		        
        	} else {
        		storePath = getFdfsStorageClient().uploadFile(Constants.GROUP_NAME, file.getInputStream(), file.getSize(), 
    					 FilenameUtils.getExtension(file.getOriginalFilename()));
			}
			// 保存文件元信息
			getFdfsStorageClient().mergeMetadata(storePath.getGroup(), storePath.getPath(), this.metaDataSet(file));
        } else {
        	storePath = getFdfsStorageClient().uploadFile(Constants.GROUP_NAME, file.getInputStream(), file.getSize(), 
					FilenameUtils.getExtension(file.getOriginalFilename()));
		}
        return storePath;
	}
	
	@Override
	public FilestoreDTO upload(MultipartFile file, int width, int height) throws Exception {
		FilestoreModel model = null;
		try {
			
			// 文件存储结果
        	StorePath storePath = this.storeFile(file, width, height);

			// 上传文件
            String uuid = UUID.randomUUID().toString();
			
			
			// 文件存储记录对象
			model = new FilestoreModel();

			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			model.setUuid(uuid);
			model.setUid(principal.getUserid());
			model.setName(file.getOriginalFilename());
			model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			model.setTo(FilestoreEnum.FDFS.getKey());
			model.setGroup(storePath.getGroup());
			model.setPath(storePath.getPath());
			if(storePath instanceof FileStorePath) {
				model.setThumb(((FileStorePath)storePath).getThumb());
			}
			getFilestoreDao().insert(model);
			
			// 文件存储信息
			FilestoreDTO attDTO = new FilestoreDTO();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(storePath.getPath());
			attDTO.setUrl(getFdfsTemplate().getAccsssURL(storePath));
			if(storePath instanceof FileStorePath) {
				attDTO.setThumb(((FileStorePath)storePath).getThumb());
				attDTO.setThumbUrl(getFdfsTemplate().getThumbAccsssURL((FileStorePath)storePath));
			}
			attDTO.setExt(model.getExt());
			
			return attDTO;

		} catch (Exception e) {
			try {
				if (model != null) {
					getFdfsStorageClient().deleteFile(model.getGroup(), model.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
	}
	
	@Override
	public List<FilestoreDTO> upload(MultipartFile[] files, int width, int height) throws Exception {

		List<FilestoreModel> modelList = Lists.newArrayList();
		List<FilestoreDTO> attList = Lists.newArrayList();
		try {
			
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			for (MultipartFile file : files) {

				// 文件存储结果
	        	StorePath storePath = this.storeFile(file, width, height);
				
				// 文件存储记录对象
				FilestoreModel model = new FilestoreModel();
				
				String uuid = UUID.randomUUID().toString();
				model.setUuid(uuid);
				model.setUid(principal.getUserid());
				model.setName(file.getOriginalFilename());
				model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
				model.setTo(FilestoreEnum.FDFS.getKey());
				model.setGroup(storePath.getGroup());
				model.setPath(storePath.getPath());
				if(storePath instanceof FileStorePath) {
					model.setThumb(((FileStorePath)storePath).getThumb());
				}
				getFilestoreDao().insert(model);
				modelList.add(model);

				// 文件存储信息
				FilestoreDTO attDTO = new FilestoreDTO();
				attDTO.setUuid(uuid);
				attDTO.setName(file.getOriginalFilename());
				attDTO.setPath(storePath.getPath());
				attDTO.setUrl(getFdfsTemplate().getAccsssURL(storePath));
				if(storePath instanceof FileStorePath) {
					attDTO.setThumb(((FileStorePath)storePath).getThumb());
					attDTO.setThumbUrl(getFdfsTemplate().getThumbAccsssURL((FileStorePath)storePath));
				}
				attDTO.setExt(model.getExt());

				attList.add(attDTO);

			}
		} catch (IOException e) {
			try {
				for (FilestoreModel model : modelList) {
					getFdfsStorageClient().deleteFile(model.getGroup(), model.getPath());
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
			getFdfsStorageClient().deleteFile(model.getGroup(), model.getPath());
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
			getFdfsStorageClient().deleteFile(model.getGroup(), model.getPath());
			getFilestoreDao().delete(model.getUuid());
		}

		return true;
	}


	@Override
	public FilestoreDTO reupload(String uuid, MultipartFile file, int width, int height) throws Exception {

		// 查询文件信息
		FilestoreModel model = getFilestoreDao().getModel(uuid);
		if(model == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		
        // 文件存储结果
    	StorePath storePath = this.storeFile(file, width, height);
		
		// 文件存储信息

        String uuid1 = UUID.randomUUID().toString();
		
        FilestoreDTO attDTO = new FilestoreDTO();
		attDTO.setUuid(uuid1);
		attDTO.setName(file.getOriginalFilename());
		attDTO.setPath(storePath.getPath());
		attDTO.setUrl(getFdfsTemplate().getAccsssURL(storePath));
		if(storePath instanceof FileStorePath) {
			attDTO.setThumb(((FileStorePath)storePath).getThumb());
			attDTO.setThumbUrl(getFdfsTemplate().getThumbAccsssURL((FileStorePath)storePath));
		}
		
		// 文件存储记录对象
		model.setUuid(uuid1);
		model.setUid(principal.getUserid());
		model.setName(file.getOriginalFilename());
		model.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
		model.setTo(FilestoreEnum.FDFS.getKey());
		model.setGroup(storePath.getGroup());
		model.setPath(storePath.getPath());
		if(storePath instanceof FileStorePath) {
			model.setThumb(((FileStorePath)storePath).getThumb());
		}
		getFilestoreDao().insert(model);

		// 删除旧的文件
		getFdfsStorageClient().deleteFile(model.getGroup(), model.getPath());
		getFilestoreDao().delete(uuid);

		attDTO.setExt(model.getExt());
		return attDTO;
	}

	
	@Override
	public List<FilestoreDTO> listByPath(List<String> paths) throws Exception {

		List<FilestoreDTO> attList = Lists.newArrayList();
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
			FilestoreDTO attDTO = new FilestoreDTO();

			attDTO.setUuid(model.getUuid());
			attDTO.setName(model.getName());
			attDTO.setPath(model.getPath());
			attDTO.setUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getPath()));
			if(StringUtils.isNoneBlank(model.getThumb())) {
				attDTO.setThumb(model.getThumb());
				attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getThumb()));
			}
			attDTO.setExt(model.getExt());
			// 文件元数据
			try {
				Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(model.getGroup(), model.getPath());
				if(!CollectionUtils.isEmpty(metaDatas)) {
					attDTO.setMetadata(metaDatas.stream().map(m -> {
						FileMetaDataDTO metaDTO = new FileMetaDataDTO();
						metaDTO.setName(m.getName());
						metaDTO.setValue(m.getValue());
						return metaDTO;
					}).collect(Collectors.toSet()));
				}
            } catch (Throwable e) {
			}

			attList.add(attDTO);

		}

		return attList;
	}
	
	@Override
	public List<FilestoreDTO> listByUuid(List<String> uuids) throws Exception {

		List<FilestoreDTO> attList = Lists.newArrayList();

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
			FilestoreDTO attDTO = new FilestoreDTO();

			attDTO.setUuid(model.getUuid());
			attDTO.setName(model.getName());
			attDTO.setPath(model.getPath());
			attDTO.setUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getPath()));
			if(StringUtils.isNoneBlank(model.getThumb())) {
				attDTO.setThumb(model.getThumb());
				attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getThumb()));
			}
			attDTO.setExt(model.getExt());
			// 文件元数据
			try {
				Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(model.getGroup(), model.getPath());
				if(!CollectionUtils.isEmpty(metaDatas)) {
					attDTO.setMetadata(metaDatas.stream().map(m -> {
						FileMetaDataDTO metaDTO = new FileMetaDataDTO();
						metaDTO.setName(m.getName());
						metaDTO.setValue(m.getValue());
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
		attDTO.setUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getPath()));
		
		// 文件元数据
		try {
			Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(model.getGroup(), model.getPath());
			if(!CollectionUtils.isEmpty(metaDatas)) {
				attDTO.setMetadata(metaDatas.stream().map(m -> {
					FileMetaDataDTO metaDTO = new FileMetaDataDTO();
					metaDTO.setName(m.getName());
					metaDTO.setValue(m.getValue());
					return metaDTO; 
				}).collect(Collectors.toSet()));
			}
		} catch (Exception e) {
		}
		
		byte[] bytes = getFdfsStorageClient().downloadFile(model.getGroup(), model.getPath(), callback);
		attDTO.setBytes(bytes);
		  
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
		attDTO.setUrl(getFdfsTemplate().getAccsssURL(model.getGroup(), model.getPath()));
		
		// 文件元数据
		try {
			Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(model.getGroup(), model.getPath());
			if(!CollectionUtils.isEmpty(metaDatas)) {
				attDTO.setMetadata(metaDatas.stream().map(m -> {
					FileMetaDataDTO metaDTO = new FileMetaDataDTO();
					metaDTO.setName(m.getName());
					metaDTO.setValue(m.getValue());
					return metaDTO; 
				}).collect(Collectors.toSet()));
			}
		} catch (Throwable e) {
		}
		
		byte[] bytes = getFdfsStorageClient().downloadFile(model.getGroup(), model.getPath(), callback);
		attDTO.setBytes(bytes);
		  
		return attDTO;
	}

	public IFilestoreDao getFilestoreDao() {
		return filestoreDao;
	}

	public FastFileStorageClient getFdfsStorageClient() {
		return fdfsStorageClient;
	}

	public FastdfsTemplate getFdfsTemplate() {
		return fdfsTemplate;
	}

	
}