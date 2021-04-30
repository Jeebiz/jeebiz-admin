/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 功能操作日志信息表Model
 */
@Alias("BizLogModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class BizLogModel extends PaginationModel<BizLogModel> {

	/**
	 * 日志id
	 */
	private String id;
	/**
	 * 功能模块
	 */
	private String module;
	/**
	 * 业务名称
	 */
	private String business;
	/**
	 * 操作类型
	 */
	private String opt;
	/**
	 * 功能操作请求来源IP地址
	 */
	private String addr;
	/**
	 * 功能操作描述
	 */
	private String msg;
	/**
	 * 功能操作异常信息
	 */
	private String exception;
	/**
	 * 功能操作人id
	 */
	private String userId;
	/**
	 * 功能操作人
	 */
	private String userName;
	/**
	 * 功能操作发生时间
	 */
	private String timestamp;
	/**
	 * 功能操作发生起始时间
	 */
	private String begintime;
	/**
	 * 功能操作发生结束时间
	 */
	private String endtime;
	/**
	 * 模糊搜索关键字
	 */
	private String keywords;
}
