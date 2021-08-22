/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import hitool.core.io.FilenameUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.jeebiz.admin.extras.filestore.bo.FileDeleteBO;
import net.jeebiz.admin.extras.filestore.bo.FileStoreBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.setup.config.JeebizFilestoreProperties;
import net.jeebiz.admin.extras.filestore.setup.provider.LocalThumbImage;
import net.jeebiz.admin.extras.filestore.utils.AttUtils;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
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
	protected <O extends FileUploadBO> List<FileDTO> handleUpload(O uploadBo) throws Exception {
		List<FileDTO> attList = Lists.newArrayList();
		for (MultipartFile file : uploadBo.getFiles()) {
			FileDTO attDTO = this.upload(uploadBo, file, uploadBo.getWidth(), uploadBo.getHeight());
			attList.add(attDTO);
		}
		return attList;
	}

	@Override
	public <O extends FileUploadBO> FileUploadResult reupload(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FileDTO upload(FileStoreBO uploadBo, MultipartFile file, int width, int height) throws Exception {
		try {
			
			// 文件存储目录
			File fileDir = AttUtils.getTargetDir(getFilestoreProperties().getUserDir(), groupName);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			};
			
			String uuid = getSequence().nextId().toString();;
			String basename = DateFormatUtils.format(System.currentTimeMillis(), "YYYYMMDD") + File.separator + uuid;
			String path = basename + FilenameUtils.getFullExtension(file.getOriginalFilename());
			
			file.transferTo(new File(fileDir, path));
			
			
			
			// 保存缩略图
			String thumbPath =  basename + new LocalThumbImage(width, height).getPrefixName() + FilenameUtils.getFullExtension(file.getOriginalFilename());
			try(FileOutputStream output = new FileOutputStream(new File(fileDir, thumbPath))){
				Thumbnails.of(file.getInputStream()).scale(width, height).toOutputStream(output);
			}
			
			// 文件存储记录对象
			
			// ip获取国家
	        String countryByIp = getIpTemplate().getCountryByIp(uploadBo.getIpAddress());
	       
	        FileEntity fileEntity = FileEntity.builder()
	    			.appId(uploadBo.getAppId())
	    			.appChannel(uploadBo.getAppChannel())
	    			.appVer(uploadBo.getAppVer())
	    			.channel(this.getChannel())
	    			.country(countryByIp)
	    			.ext(FilenameUtils.getExtension(file.getOriginalFilename()))
	    			.name(file.getOriginalFilename())
	    			.store(FilestoreChannel.LOCAL.getKey())
	    			.group1(groupName)
	    			.path(path)
	    			.thumb(thumbPath)
	    			.ipAddress(uploadBo.getIpAddress())
	    			.userId(uploadBo.getUserId())
	    			.uuid(uuid)
	    			.build();
	        fileEntity.setCreateTime(new Date());
	        fileEntity.setCreator(uploadBo.getUserId());
	        getFileMapper().insert(fileEntity);
			
			// 文件存储信息
			FileDTO attDTO = new FileDTO();
			//attDTO.setUuid(uuid);
			attDTO.setName(file.getOriginalFilename());
			attDTO.setPath(path);
			//attDTO.setUrl(getOssTemplate().getAccsssURL(storePath));
			if(StringUtils.isNotBlank(thumbPath)) {
				attDTO.setThumb(thumbPath);
				//attDTO.setThumbUrl(getOssTemplate().getThumbAccsssURL(storePath));
			}
			attDTO.setExt(fileEntity.getExt());
			
			return attDTO;
			
		} catch (Exception e) {
			throw new BizRuntimeException("测试报告附件存储IO异常");
		}
	}

	public JeebizFilestoreProperties getFilestoreProperties() {
		return filestoreProperties;
	}
	
	@Override
	public <O extends FileDeleteBO> boolean delete(O deleteBo) throws Exception {
		return false;
	}

}
