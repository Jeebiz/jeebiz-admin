/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.setup.config.JeebizFilestoreProperties;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;

@Component
public class LocalFilestoreStrategy implements FilestoreStrategy {

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
	public <O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <O extends FileUploadBO> FileUploadResult reupload(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <O extends FileUploadBO> boolean check(O uploadBo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


}
