/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.logbiz.dao.entities.LoginLogModel;
import io.hiwepy.boot.api.dao.BaseMapper;

/**
 * 
 */		
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLogModel>{
	
	
}
