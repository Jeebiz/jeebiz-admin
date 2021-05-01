/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.logbiz.dao.entities.BizLogModel;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 
 */		
@Mapper
public interface IBizLogDao extends BaseMapper<BizLogModel>{
	
	
}
