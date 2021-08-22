/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.provider;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import hitool.core.collections.CollectionUtils;
import hitool.core.io.FilenameUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.setup.config.JeebizFilestoreProperties;
import net.jeebiz.admin.extras.filestore.utils.AttUtils;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.boot.api.exception.BizRuntimeException;

public class LocalFilestoreProvider implements FilestoreProvider {

	private IFileMapper fileMapper;
	private JeebizFilestoreProperties filestoreProperties;
	private final String groupName = "files";
	
	public LocalFilestoreProvider(IFileMapper fileMapper, JeebizFilestoreProperties filestoreProperties) {
		this.fileMapper = fileMapper;
		this.filestoreProperties = filestoreProperties;
	}

	@Override
	public FilestoreChannel getProvider() {
		return FilestoreChannel.LOCAL;
	}
	
	@Override
	public FilestoreConfig getConfig() {
		 
	}
	
	@Override
	public FileDTO upload(String uid, MultipartFile file, int width, int height) throws Exception {
		try {
			
			// 文件存储目录
			File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), groupName);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			};
			
			String uuid = UUID.randomUUID().toString();
			String basename = DateFormatUtils.format(System.currentTimeMillis(), "YYYYMMDD") + File.separator + uuid;
			String path = basename + FilenameUtils.getFullExtension(file.getOriginalFilename());
			
			file.transferTo(new File(fileDir, path));
			
			// 保存缩略图
			String thumbPath =  basename + new LocalThumbImage(width, height).getPrefixName() + FilenameUtils.getFullExtension(file.getOriginalFilename());
			try(FileOutputStream output = new FileOutputStream(new File(fileDir, thumbPath))){
				Thumbnails.of(file.getInputStream()).scale(width, height).toOutputStream(output);
			}
			
			// 文件存储记录对象
			FileEntity entity = new FileEntity();

			entity.setUid(uid);
			entity.setUuid(uuid);
			entity.setName(file.getOriginalFilename());
			entity.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			entity.setStore(FilestoreChannel.LOCAL.getKey());
			entity.setGroup1(groupName);
			entity.setPath(path);
			entity.setThumb(thumbPath);
			getFileMapper().insert(entity);
			
			// 文件存储信息
			FileDTO attDTO = new FileDTO();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(path);
			//attDTO.setUrl(getOssTemplate().getAccsssURL(storePath));
			if(StringUtils.isNotBlank(thumbPath)) {
				attDTO.setThumb(thumbPath);
				//attDTO.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
			}
			attDTO.setExt(entity.getExt());
			
			return attDTO;
			
		} catch (Exception e) {
			throw new BizRuntimeException("测试报告附件存储IO异常");
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
		// 文件存储目录
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), groupName);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		// 删除服务器文件，如果出现异常将会回滚前面的操作
		for (FileEntity entity : fileList) {
			
			// 删除旧的文件
			File oldFile = new File(fileDir, entity.getPath()); 
			if(oldFile.exists()) {
				oldFile.delete();
			}
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
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), entity.getGroup1());
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		File oldFile = new File(fileDir, entity.getPath()); 
		if(oldFile.exists()) {
			oldFile.delete();
		}
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
	
	private List<FileDTO> mapperFile(List<FileEntity> fileList) {
		List<FileDTO> attList = Lists.newArrayList();
		fileList = fileList.stream().sorted().collect(Collectors.toList());
		// 循环进行对象转换
		for (FileEntity entity : fileList) {
			
			File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), entity.getGroup1());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			};
			
			// 文件存储信息
			FileDTO attDTO = new FileDTO();

			attDTO.setUuid(entity.getUuid());
			attDTO.setName(entity.getName());
			attDTO.setPath(entity.getPath());
			//attDTO.setUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getPath()));
			if(StringUtils.isNoneBlank(entity.getThumb())) {
				attDTO.setThumb(entity.getThumb());
				//attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getThumb()));
			}
			attDTO.setExt(entity.getExt());
			// 文件元数据
			try {
				attDTO.setMetadata(FilestoreUtils.metaDataSet(new File(fileDir, entity.getPath())));
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
		attDTO.setUrl("");
		
		// 文件存储目录
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), entity.getGroup1());
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		attDTO.setBytes(FileCopyUtils.copyToByteArray(new File(fileDir, entity.getPath())));
		  
		return attDTO;
	}
	
	public IFileMapper getFileMapper() {
		return fileMapper;
	}

	public JeebizFilestoreProperties getFilestoreProperties() {
		return filestoreProperties;
	}
	
}
