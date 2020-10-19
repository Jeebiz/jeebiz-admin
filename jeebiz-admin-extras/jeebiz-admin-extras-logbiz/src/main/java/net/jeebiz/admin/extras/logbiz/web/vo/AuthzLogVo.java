/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 认证授权日志信息Vo
 */
@ApiModel(value = "AuthzLogVo", description = "认证授权日志信息Vo")
@Getter
@Setter
@ToString
public class AuthzLogVo {

	/**
	 * 日志ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "日志记录ID")
	private String id;
	/**
	 * 认证授权对象ID
	 */
	@ApiModelProperty(name = "userId", dataType = "String", value = "认证授权对象ID")
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
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
	 */
	@ApiModelProperty(name = "protocol", dataType = "String", value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等")
	private String protocol;
	/**
	 * 负责此次认证授权的realm名称
	 */
	@ApiModelProperty(name = "realm", dataType = "String", value = "负责此次认证授权的realm名称")
	private String realm;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）")
	private String level;
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
