/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.shadow.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;

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
