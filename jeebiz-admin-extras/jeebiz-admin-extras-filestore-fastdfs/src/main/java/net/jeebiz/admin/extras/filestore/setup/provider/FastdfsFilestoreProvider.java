/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.biz.utils.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.setup.Constants;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileMetaDataDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CollectionUtils;

public class FastdfsFilestoreProvider implements FilestoreProvider {
	
	private DownloadByteArray callback = new DownloadByteArray();
	private IFileMapper fileMapper;
	private FastFileStorageClient fdfsStorageClient;
	private FastdfsTemplate fdfsTemplate;

	public FastdfsFilestoreProvider(IFileMapper fileMapper, 
									FastFileStorageClient fdfsStorageClient,
									FastdfsTemplate fdfsTemplate) {
		this.fileMapper = fileMapper;
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
		try ( InputStream inputStream = file.getInputStream(); ) {
			Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
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
	
	protected StorePath storeFile( MultipartFile file, int width, int height) throws IOException {
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
                    .toGroup(Constants.GROUP_name)
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
        		storePath = getFdfsStorageClient().uploadFile(Constants.GROUP_name, file.getInputStream(), file.getSize(), 
    					 FilenameUtils.getExtension(file.getOriginalFilename()));
			}
			// 保存文件元信息
			getFdfsStorageClient().mergeMetadata(storePath.getGroup(), storePath.getPath(), this.metaDataSet(file));
        } else {
        	storePath = getFdfsStorageClient().uploadFile(Constants.GROUP_name, file.getInputStream(), file.getSize(), 
					FilenameUtils.getExtension(file.getOriginalFilename()));
		}
        return storePath;
	}
	
	@Override
	public FileDTO upload(String uid, MultipartFile file, int width, int height) throws Exception {
		FileEntity entity = null;
		try {
			
			// 文件存储结果
        	StorePath storePath = this.storeFile(file, width, height);

			// 上传文件
            String uuid = UUID.randomUUID().toString();
			
			// 文件存储记录对象
			entity = new FileEntity();

			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			entity.setUuid(uuid);
			entity.setUid(principal.getUserid());
			entity.setName(file.getOriginalFilename());
			entity.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			entity.setTo(FilestoreEnum.FDFS.getKey());
			entity.setGroup(storePath.getGroup());
			entity.setPath(storePath.getPath());
			if(storePath instanceof FileStorePath) {
				entity.setThumb(((FileStorePath)storePath).getThumb());
			}
			getFileMapper().insert(entity);
			
			// 文件存储信息
			FileDTO attDTO = new FileDTO();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(storePath.getPath());
			attDTO.setUrl(getFdfsTemplate().getAccsssURL(storePath));
			if(storePath instanceof FileStorePath) {
				attDTO.setThumb(((FileStorePath)storePath).getThumb());
				attDTO.setThumbUrl(getFdfsTemplate().getThumbAccsssURL((FileStorePath)storePath));
			}
			attDTO.setExt(entity.getExt());
			
			return attDTO;

		} catch (Exception e) {
			try {
				if (entity != null) {
					getFdfsStorageClient().deleteFile(entity.getGroup(), entity.getPath());
				}
			} catch (Exception e1) {
				// 忽略删除异常
			}
			throw e;
		}
	}
	
	@Override
	public List<FileDTO> upload(String uid, MultipartFile[] files, int width, int height) throws Exception {
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
			getFdfsStorageClient().deleteFile(entity.getGroup(), entity.getPath());
			getFileMapper().deleteById(entity.getId());
		}
		return true;
	}

	@Override
	public FileDTO reupload(String uid, String uuid, MultipartFile file, int width, int height) throws Exception {

		// 查询旧文件信息
		FileEntity entity = getFileMapper().selectOne(new QueryWrapper<FileEntity>().eq("f_uuid", uuid));
		if(entity == null) {
			throw new BizRuntimeException(uuid + "指向的文件不存在");
		}
		
		// 上传新文件
		FileDTO attDTO = this.upload(uid, file, width, height);
		
		// 删除旧的文件
		getFdfsStorageClient().deleteFile(entity.getGroup(), entity.getPath());
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
			attDTO.setUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getPath()));
			if(StringUtils.isNoneBlank(entity.getThumb())) {
				attDTO.setThumb(entity.getThumb());
				attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getThumb()));
			}
			attDTO.setExt(entity.getExt());
			// 文件元数据
			try {
				Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(entity.getGroup(), entity.getPath());
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
		attDTO.setUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getPath()));
		
		// 文件元数据
		try {
			Set<MetaData> metaDatas = getFdfsStorageClient().getMetadata(entity.getGroup(), entity.getPath());
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
		
		byte[] bytes = getFdfsStorageClient().downloadFile(entity.getGroup(), entity.getPath(), callback);
		attDTO.setBytes(bytes);
		  
		return attDTO;
	}

	public IFileMapper getFileMapper() {
		return fileMapper;
	}

	public FastFileStorageClient getFdfsStorageClient() {
		return fdfsStorageClient;
	}

	public FastdfsTemplate getFdfsTemplate() {
		return fdfsTemplate;
	}

	
}