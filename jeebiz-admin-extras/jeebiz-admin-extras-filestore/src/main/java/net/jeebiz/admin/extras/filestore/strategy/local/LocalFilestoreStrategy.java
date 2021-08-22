/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.strategy.local;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import hitool.core.io.FilenameUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.jeebiz.admin.extras.filestore.bo.FileData;
import net.jeebiz.admin.extras.filestore.bo.FileDownloadResult;
import net.jeebiz.admin.extras.filestore.bo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.setup.config.JeebizFilestoreProperties;
import net.jeebiz.admin.extras.filestore.strategy.AbstractFilestoreStrategy;
import net.jeebiz.admin.extras.filestore.utils.AttUtils;
import net.jeebiz.admin.extras.filestore.utils.FilestoreUtils;
import net.jeebiz.boot.api.exception.BizRuntimeException;

@Component
public class LocalFilestoreStrategy extends AbstractFilestoreStrategy {

	@Autowired
	private JeebizFilestoreProperties filestoreProperties;
	
	private final String groupName = "files";
	
	@Override
	public FilestoreChannel getChannel() {
		return FilestoreChannel.LOCAL;
	}
	
	@Override
	public FilestoreConfig getConfig() {
		FilestoreConfig config = new FilestoreConfig();
		config.setEndpoint(filestoreProperties.getEndpoint());
		return config;
	}
	
	@Override
	protected FileData handleFileUpload(MultipartFile file, int width, int height) throws Exception {
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
			
			// 文件存储信息
			FileData attDTO = new FileData();
			attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(path);
			//attDTO.setUrl(getOssTemplate().getAccsssURL(storePath));
			if(StringUtils.isNotBlank(thumbPath)) {
				attDTO.setThumb(thumbPath);
				//attDTO.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
			}
			attDTO.setExt(FilenameUtils.getExtension(file.getOriginalFilename()));
			
			return attDTO;
			
		} catch (Exception e) {
			throw new BizRuntimeException("测试报告附件存储IO异常");
		}
	}
	
	@Override
	protected boolean handleFileDelete(FileEntity fileEntity) throws Exception {
		// 文件存储目录
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), groupName);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		// 删除旧的文件
		File oldFile = new File(fileDir, fileEntity.getPath()); 
		if(oldFile.exists()) {
			oldFile.delete();
		}
		return true;
	}
	
	@Override
	protected FileData handleFileMetadata(FileEntity entity) {
		
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), entity.getGroup1());
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};
		
		// 文件存储信息
		FileData fileDTO = new FileData();

		fileDTO.setUuid(entity.getUuid());
		fileDTO.setName(entity.getName());
		fileDTO.setPath(entity.getPath());
		//attDTO.setUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getPath()));
		if(StringUtils.isNoneBlank(entity.getThumb())) {
			fileDTO.setThumb(entity.getThumb());
			//attDTO.setThumbUrl(getFdfsTemplate().getAccsssURL(entity.getGroup(), entity.getThumb()));
		}
		fileDTO.setExt(entity.getExt());
		// 文件元数据
		try {
			fileDTO.setMetadata(FilestoreUtils.metaDataSet(new File(fileDir, entity.getPath())));
		} catch (Exception e) {
		}
		return fileDTO;
	}
	
	@Override
	protected void handleFileDownload(FileEntity entity, FileDownloadResult downloadRt) throws Exception {
		
		File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), entity.getGroup1());
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		};

		downloadRt.setBytes(FileCopyUtils.copyToByteArray(new File(fileDir, entity.getPath())));
		
	}
	
	public JeebizFilestoreProperties getFilestoreProperties() {
		return filestoreProperties;
	}

}
