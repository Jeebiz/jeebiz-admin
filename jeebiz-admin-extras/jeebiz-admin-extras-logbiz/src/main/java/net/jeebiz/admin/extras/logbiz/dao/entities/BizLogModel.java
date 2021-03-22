/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao.entities;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 功能操作日志信息表Model
 */
@Alias("BizLogModel")
@SuppressWarnings("serial")
public class BizLogModel extends PaginationModel {

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
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	private String level;
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
	 * 功能操作人ID
	 */
	private String userId;
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
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
