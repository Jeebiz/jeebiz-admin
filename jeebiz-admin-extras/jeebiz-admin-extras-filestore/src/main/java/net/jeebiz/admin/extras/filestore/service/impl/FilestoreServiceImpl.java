/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.admin.extras.filestore.setup.provider.FilestoreProvider;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FileDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FileDownloadDTO;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class FilestoreServiceImpl extends BaseMapperServiceImpl<FileEntity, IFileMapper> implements IFilestoreService{
	
	@Autowired
	private FilestoreProvider filestoreProvider;
	
	@Override
	public FilestoreConfig getConfig() {
		return getFilestoreProvider().getConfig();
	};
	
	@Override
	public FileDTO upload(String uid, MultipartFile file, int width, int height) throws Exception {
		return getFilestoreProvider().upload(uid, file, width, height);
	}
	
	@Override
	public List<FileDTO> upload(String uid, MultipartFile[] files, int width, int height) throws Exception {
		return getFilestoreProvider().upload(uid, files, width, height);
	}

	@Override
	public boolean deleteByPath(List<String> paths) throws Exception {
		return getFilestoreProvider().deleteByPath(paths);
	}
	
	@Override
	public boolean deleteByUuid(List<String> uuids) throws Exception {
		return getFilestoreProvider().deleteByUuid(uuids);
	}

	@Override
	public FileDTO reupload(String uid, String uuid, MultipartFile file, int width, int height) throws Exception {
		return getFilestoreProvider().reupload(uid, uuid, file, width, height);
	}
	
	@Override
	public List<FileDTO> listByPath(List<String> paths) throws Exception {
		return getFilestoreProvider().listByPath(paths);
	}

	@Override
	public List<FileDTO> listByUuid(List<String> uuids) throws Exception {
		return getFilestoreProvider().listByUuid(uuids);
	}
	
	@Override
	public FileDownloadDTO downloadByPath(String path) throws Exception {
		return getFilestoreProvider().downloadByPath(path); 
	}

	@Override
	public FileDownloadDTO downloadByUuid(String uuid) throws Exception {
		return getFilestoreProvider().downloadByUuid(uuid);
	}

	public FilestoreProvider getFilestoreProvider() {
		return filestoreProvider;
	}
	 
}
