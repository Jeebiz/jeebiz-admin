/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.logbiz.dao.entities.AuthzLogModel;
import net.jeebiz.boot.api.dao.BaseDao;

/**
 * 
 */		
@Mapper
public interface IAuthzLogDao extends BaseDao<AuthzLogModel>{
	
	
}
