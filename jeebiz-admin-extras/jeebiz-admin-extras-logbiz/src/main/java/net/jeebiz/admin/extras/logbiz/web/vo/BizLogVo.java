/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 业务操作日志信息Vo
 */
@ApiModel(value = "BizLogVo", description = "业务操作日志信息Vo")
public class BizLogVo {

	/**
	 * 日志ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "日志记录ID")
	private String id;
	/**
	 * 功能模块
	 */
	@ApiModelProperty(name = "module", dataType = "String", value = "功能模块名称")
	private String module;
	/**
	 * 业务名称
	 */
	@ApiModelProperty(name = "business", dataType = "String", value = "业务名称")
	private String business;
	/**
	 * 操作类型
	 */
	@ApiModelProperty(name = "opt", dataType = "String", value = "操作类型")
	private String opt;
	/**
	 * 操作描述
	 */
	@ApiModelProperty(name = "desc", dataType = "String", value = "服务接口访问结果状态：1：成功|0:异常")
	private String desc;
	/**
	 * 服务接口访问异常错误对照码
	 */
	@ApiModelProperty(name = "errorCode", dataType = "String", value = "服务接口访问异常错误对照码")
	private String errorCode;
	/**
	 * 服务接口访问异常信息
	 */
	@ApiModelProperty(name = "exception", dataType = "String", value = "服务接口访问异常信息")
	private String exception;
	/**
	 * 操作人ID
	 */
	@ApiModelProperty(name = "userId", dataType = "String", value = "操作人ID")
	private String userId;
	/**
	 * 操作发生时间
	 */
	@ApiModelProperty(name = "timestamp", dataType = "String", value = "服务接口访问发生时间")
	private String timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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

}
