/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.admin.extras.filestore.service.IFilestoreService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class FilestoreServiceImpl extends BaseMapperServiceImpl<FileEntity, IFileMapper> implements IFilestoreService{
	 
}
