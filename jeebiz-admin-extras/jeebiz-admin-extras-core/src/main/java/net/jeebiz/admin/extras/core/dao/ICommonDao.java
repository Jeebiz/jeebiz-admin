/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.core.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.boot.api.dao.entities.PairModel;

/**
 * 公共查询Dao
 */
@Mapper
public interface ICommonDao extends BaseDao<PairModel> {

	/**
	 *  数据库当前时间 ： 
	 * @return
	 */
	String getNow();
	
	/**
	 * sys_guid() 
	 * @return
	 */
	String getSysGuid();
	
}
