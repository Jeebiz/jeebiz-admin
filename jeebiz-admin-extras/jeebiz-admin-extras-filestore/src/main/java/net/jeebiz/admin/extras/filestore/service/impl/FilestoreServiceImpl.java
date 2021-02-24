/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.admin.extras.filestore.setup.provider.FilestoreProvider;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDownloadDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class FilestoreServiceImpl extends BaseServiceImpl<FilestoreModel, IFilestoreDao> implements IFilestoreService{
	
	@Autowired
	private FilestoreProvider filestoreProvider;
	
	@Override
	public FilestoreConfig getConfig() {
		return getFilestoreProvider().getConfig();
	};
	
	@Override
	public FilestoreDTO upload(MultipartFile file, int width, int height) throws Exception {
		return getFilestoreProvider().upload(file, width, height);
	}
	
	@Override
	public List<FilestoreDTO> upload(MultipartFile[] files, int width, int height) throws Exception {
		return getFilestoreProvider().upload(files, width, height);
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
	public FilestoreDTO reupload(String uuid, MultipartFile file, int width, int height) throws Exception {
		return getFilestoreProvider().reupload(uuid, file, width, height);
	}
	
	@Override
	public List<FilestoreDTO> listByPath(List<String> paths) throws Exception {
		return getFilestoreProvider().listByPath(paths);
	}

	@Override
	public List<FilestoreDTO> listByUuid(List<String> uuids) throws Exception {
		return getFilestoreProvider().listByUuid(uuids);
	}
	
	@Override
	public FilestoreDownloadDTO downloadByPath(String path) throws Exception {
		return getFilestoreProvider().downloadByPath(path); 
	}

	@Override
	public FilestoreDownloadDTO downloadByUuid(String uuid) throws Exception {
		return getFilestoreProvider().downloadByUuid(uuid);
	}

	public FilestoreProvider getFilestoreProvider() {
		return filestoreProvider;
	}
	 
}
