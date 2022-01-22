/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.filestore.dao.FileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class FilestoreServiceImpl extends BaseServiceImpl<FileMapper, FileEntity> implements IFilestoreService{
	 
}
