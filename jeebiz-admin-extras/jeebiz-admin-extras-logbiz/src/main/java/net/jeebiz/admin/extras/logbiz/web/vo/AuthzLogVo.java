/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 认证授权日志信息Vo
 */
@ApiModel(value = "AuthzLogVo", description = "认证授权日志信息Vo")
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

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
