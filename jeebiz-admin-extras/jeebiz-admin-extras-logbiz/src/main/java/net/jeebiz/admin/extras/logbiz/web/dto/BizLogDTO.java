/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 业务操作日志信息DTO
 */
@ApiModel(value = "BizLogDTO", description = "业务操作日志信息DTO")
@Getter
@Setter
@ToString
public class BizLogDTO {

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
	 * 功能操作人
	 */
	@ApiModelProperty(name = "userName", dataType = "String", value = "功能操作人")
	private String userName;
	/**
	 * 操作发生时间
	 */
	@ApiModelProperty(name = "timestamp", dataType = "String", value = "服务接口访问发生时间")
	private String timestamp;

}
