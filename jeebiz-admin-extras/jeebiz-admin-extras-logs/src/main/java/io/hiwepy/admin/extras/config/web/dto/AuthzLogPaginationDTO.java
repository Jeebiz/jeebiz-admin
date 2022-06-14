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
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

/**
 * 认证授权日志数据筛选条件DTO
 */
@ApiModel(value = "AuthzLogPaginationDTO", description = "认证授权日志数据筛选条件DTO")
@Getter
@Setter
@ToString
public class AuthzLogPaginationDTO extends AbstractPaginationDTO {

	/**
	 * 认证授权类型（login:登录认证、logout:会话注销）
	 */
	@ApiModelProperty(name = "opt", dataType = "String", value = "认证授权类型（login:登录认证、logout:会话注销）", allowableValues = "login,logout")
	private String opt;
	/**
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等
	 */
	@ApiModelProperty(name = "protocol", dataType = "String", value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等")
	private String protocol;
	/**
	 * 认证请求来源IP地址
	 */
	@ApiModelProperty(name = "addr", dataType = "String", value = "认证请求来源IP地址", allowableValues = "1,0")
	private String addr;
	/**
	 * 认证授权结果：（fail:失败、success:成功）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "认证授权结果：（fail:失败、success:成功）", allowableValues = "fail,success")
	private String status;
	/**
	 * 认证授权起始时间
	 */
	@ApiModelProperty(name = "begintime", dataType = "String", value = "认证授权起始时间")
	private String begintime;
	/**
	 * 认证授权结束时间
	 */
	@ApiModelProperty(name = "endtime", dataType = "String", value = "认证授权结束时间")
	private String endtime;
	/**
	 * 模糊搜索关键字
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "模糊搜索关键字")
	private String keywords;
}
