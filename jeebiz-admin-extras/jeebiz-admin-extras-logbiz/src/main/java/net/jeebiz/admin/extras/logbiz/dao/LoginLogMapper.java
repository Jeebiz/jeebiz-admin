/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.logbiz.dao.entities.LoginLogModel;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 
 */		
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLogModel>{
	
	
}