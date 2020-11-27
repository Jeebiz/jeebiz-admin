/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.dao.entities.FilestoreModel;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.admin.extras.filestore.setup.FilestoreProperties;
import net.jeebiz.admin.extras.filestore.setup.provider.FilestoreProvider;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDTO;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreDownloadDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class FilestoreServiceImpl extends BaseServiceImpl<FilestoreModel, IFilestoreDao> implements IFilestoreService{
	
	@Autowired
	private List<FilestoreProvider> filestoreProviders;
	@Autowired
	private FilestoreProperties filestoreProperties;
	
	@Override
	public FilestoreConfig getConfig() {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.getConfig();
			}
		}
		return null;
	};
	
	
	@Override
	public FilestoreDTO upload(MultipartFile file, int width, int height) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.upload(file, width, height);
			}
		}
		return null;
	}
	
	@Override
	public List<FilestoreDTO> upload(MultipartFile[] files, int width, int height) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.upload(files, width, height);
			}
		}
		return Lists.newArrayList();
	}

	@Override
	public boolean deleteByPath(List<String> paths) throws Exception {
		for (FilestoreProvider provider : filestoreProviders) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.deleteByPath(paths);
			}
		}
		return false;
	}
	
	@Override
	public boolean deleteByUuid(List<String> uuids) throws Exception {
		for (FilestoreProvider provider : filestoreProviders) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.deleteByUuid(uuids);
			}
		}
		return false;
	}

	@Override
	public FilestoreDTO reupload(String uuid, MultipartFile file, int width, int height) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.reupload(uuid, file, width, height);
			}
		}
		return null;
	}
	
	@Override
	public List<FilestoreDTO> listByPath(List<String> paths) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.listByPath(paths);
			}
		}
		return null;
	}

	@Override
	public List<FilestoreDTO> listByUuid(List<String> uuids) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.listByUuid(uuids);
			}
		}
		return null;
	}
	
	@Override
	public FilestoreDownloadDTO downloadByPath(String path) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.downloadByPath(path); 
			}
		}
		return null;
	}

	@Override
	public FilestoreDownloadDTO downloadByUuid(String uuid) throws Exception {
		for (FilestoreProvider provider : getFilestoreProviders()) {
			if(provider.getProvider().equals(getFilestoreProperties().getStorage())) {
				return provider.downloadByUuid(uuid);
			}
		}
		return null;
	}

	public List<FilestoreProvider> getFilestoreProviders() {
		return filestoreProviders;
	}

	public FilestoreProperties getFilestoreProperties() {
		return filestoreProperties;
	}
	 
}
