/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统异常日志信息Vo
 */
@ApiModel(value = "BizExcpVo", description = "系统异常日志信息Vo")
public class BizExcpVo {

	/**
	 * 日志ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "日志记录ID")
	private String id;
	/**
	 * 系统异常操作人ID
	 */
	@ApiModelProperty(name = "userId", dataType = "String", value = "系统异常操作人ID")
	private String userId;
	/**
	 * 系统异常发生源类型
	 */
	@ApiModelProperty(name = "cpmt", dataType = "String", value = "系统异常发生源类型")
	private String cpmt;
	/**
	 * 系统异常对象
	 */
	@ApiModelProperty(name = "clazz", dataType = "String", value = "系统异常对象")
	private String clazz;
	/**
	 * 系统异常类型
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "系统异常类型")
	private String type;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	private String level;
	/**
	 * 导致系统异常的请求来源IP地址
	 */
	@ApiModelProperty(name = "addr", dataType = "String", value = "导致系统异常的请求来源IP地址")
	private String addr;
	/**
	 * 系统异常代码
	 */
	@ApiModelProperty(name = "code", dataType = "String", value = "系统异常代码")
	private String code;
	/**
	 * 系统异常描述
	 */
	@ApiModelProperty(name = "msg", dataType = "String", value = "系统异常描述")
	private String msg;
	/**
	 * 系统异常信息
	 */
	@ApiModelProperty(name = "exception", dataType = "String", value = "系统异常信息")
	private String exception;
	/**
	 * 系统异常发生时间
	 */
	@ApiModelProperty(name = "timestamp", dataType = "String", value = "系统异常发生时间")
	private String timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}
