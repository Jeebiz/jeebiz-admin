/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.logbiz.dao.entities.BizExcpModel;
import net.jeebiz.boot.api.dao.BaseDao;

/**
 * 
 */		
@Mapper
public interface IBizExcpDao extends BaseDao<BizExcpModel>{

	List<Map<String, String>> getExcpTypes();
	
}
