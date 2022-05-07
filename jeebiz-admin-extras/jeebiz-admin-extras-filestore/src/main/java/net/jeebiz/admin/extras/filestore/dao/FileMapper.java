/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.filestore.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.filestore.dao.entities.FileEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface FileMapper extends BaseMapper<FileEntity>{
	
}
