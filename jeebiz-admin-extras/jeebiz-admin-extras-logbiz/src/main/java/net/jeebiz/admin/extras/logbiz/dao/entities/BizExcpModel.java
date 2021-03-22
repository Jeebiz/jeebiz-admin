/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao.entities;

import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 系统异常日志信息表Model
 */
@Alias("BizExcpModel")
@SuppressWarnings("serial")
public class BizExcpModel extends PaginationModel {

	/**
	 * 系统异常操作人ID
	 */
	private String userId;
	/**
	 * 系统异常发生源类型
	 */
	private String cpmt;
	/**
	 * 系统异常对象
	 */
	private String clazz;
	/**
	 * 系统异常类型
	 */
	private String type;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	private String level;
	/**
	 * 导致系统异常的请求来源IP地址
	 */
	private String addr;
	/**
	 * 系统异常代码
	 */
	private String code;
	/**
	 * 系统异常描述
	 */
	private String msg;
	/**
	 * 系统异常信息
	 */
	private String exception;
	/**
	 * 系统异常发生时间
	 */
	private String timestamp;
	/**
	 * 系统异常发生起始时间
	 */
	private String begintime;
	/**
	 * 系统异常发生结束时间
	 */
	private String endtime;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCpmt() {
		return cpmt;
	}

	public void setCpmt(String cpmt) {
		this.cpmt = cpmt;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
