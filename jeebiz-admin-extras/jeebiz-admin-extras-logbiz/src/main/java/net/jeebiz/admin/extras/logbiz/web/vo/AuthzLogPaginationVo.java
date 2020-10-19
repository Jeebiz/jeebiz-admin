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
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

/**
 * 认证授权日志数据筛选条件Vo
 */
@ApiModel(value = "AuthzLogPaginationVo", description = "认证授权日志数据筛选条件Vo")
@Getter
@Setter
@ToString
public class AuthzLogPaginationVo extends AbstractPaginationVo {

	/**
	 * 认证授权类型（login:登录认证、logout:会话注销）
	 */
	@ApiModelProperty(name = "opt", dataType = "String", value = "认证授权类型（login:登录认证、logout:会话注销）", allowableValues = "login,logout")
	private String opt;
	/**
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
	 */
	@ApiModelProperty(name = "protocol", dataType = "String", value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等")
	private String protocol;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）", allowableValues = "debug,info,warn,error,fetal")
	private String level;
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
