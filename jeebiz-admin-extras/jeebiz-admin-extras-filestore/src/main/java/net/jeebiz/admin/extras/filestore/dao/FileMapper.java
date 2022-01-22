/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.filestore.dao.entities.FileEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface FileMapper extends BaseMapper<FileEntity>{
	
}
