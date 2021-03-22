/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.web.vo;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

/**
 * 认证授权日志数据筛选条件Vo
 */
@ApiModel(value = "AuthzLogPaginationVo", description = "认证授权日志数据筛选条件Vo")
public class AuthzLogPaginationVo extends AbstractPaginationVo {

	/**
	 * 认证授权类型（login:登录认证、logout:会话注销）
	 */
	@ApiModelProperty(name = "opt", dataType = "String", value = "认证授权类型（login:登录认证、logout:会话注销）", allowableValues = "login,logout")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String opt;
	/**
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
	 */
	@ApiModelProperty(name = "protocol", dataType = "String", value = "认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String protocol;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	@ApiModelProperty(name = "level", dataType = "String", value = "日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）", allowableValues = "debug,info,warn,error,fetal")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String level;
	/**
	 * 认证请求来源IP地址
	 */
	@ApiModelProperty(name = "addr", dataType = "String", value = "认证请求来源IP地址", allowableValues = "1,0")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String addr;
	/**
	 * 认证授权结果：（fail:失败、success:成功）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "认证授权结果：（fail:失败、success:成功）", allowableValues = "fail,success")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String status;
	/**
	 * 认证授权起始时间
	 */
	@ApiModelProperty(name = "begintime", dataType = "String", value = "认证授权起始时间")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String begintime;
	/**
	 * 认证授权结束时间
	 */
	@ApiModelProperty(name = "endtime", dataType = "String", value = "认证授权结束时间")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String endtime;
	
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
