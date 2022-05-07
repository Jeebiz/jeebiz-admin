/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.filestore.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.filestore.dao.FileMapper;
import io.hiwepy.admin.extras.filestore.dao.entities.FileEntity;
import io.hiwepy.admin.extras.filestore.service.IFilestoreService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class FilestoreServiceImpl extends BaseServiceImpl<FileMapper, FileEntity> implements IFilestoreService{
	 
}
