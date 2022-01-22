/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.boot.api.dao.BaseMapper;
import net.jeebiz.boot.api.dao.entities.PairModel;

/**
 * 公共查询Mapper
 */
@Mapper
public interface CommonMapper extends BaseMapper<PairModel> {


	/**
	 * 数据库当前秒
	 * @return 数据库当前秒
	 */
	Long getNow();

	/**
	 *  数据库当前毫秒 ：
	 * @return 数据库当前毫秒
	 */
	Long getNowMillis();

	/**
	 *  数据库当前时间 ： 
	 * @return 数据库当前时间
	 */
	String getNowString();

}
