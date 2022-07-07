/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 认证授权日志信息DTO
 */
@ApiModel(value = "AuthzLogDTO", description = "认证授权日志信息DTO")
@Getter
@Setter
@ToString
public class AuthzLogDTO {

	/**
	 * 日志id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "日志记录id")
	private String id;
	/**
	 * 认证授权对象id
	 */
	@ApiModelProperty(name = "userId", dataType = "String", value = "认证授权对象id")
	private String userId;
	/**
	 * 认证授权对象
	 */
	@ApiModelProperty(name = "userName", dataType = "String", value = "认证授权对象")
	private String userName;
	/**
	 * 认证授权类型（login:登录认证、logout:会话注销）
	 */
	@ApiModelProperty(name = "opt", dataType = "String", value = "认证授权类型（login:登录认证、logout:会话注销）")
	private String opt;
	/**
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等
	 */
	@ApiModelProperty(name = "protocol", dataType = "String", value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等")
	private String protocol;
	/**
	 * 认证请求来源IP地址
	 */
	@ApiModelProperty(name = "addr", dataType = "String", value = "认证请求来源IP地址")
	private String addr;
	/**
	 * 认证授权结果：（fail:失败、success:成功）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "认证授权结果：（fail:失败、success:成功）")
	private String status;
	/**
	 * 认证授权请求信息
	 */
	@ApiModelProperty(name = "msg", dataType = "String", value = "认证授权请求信息")
	private String msg;
	/**
	 * 认证授权异常信息
	 */
	@ApiModelProperty(name = "exception", dataType = "String", value = "认证授权异常信息")
	private String exception;
	/**
	 * 认证授权发生时间
	 */
	@ApiModelProperty(name = "timestamp", dataType = "String", value = "认证授权发生时间")
	private String timestamp;
	
}
