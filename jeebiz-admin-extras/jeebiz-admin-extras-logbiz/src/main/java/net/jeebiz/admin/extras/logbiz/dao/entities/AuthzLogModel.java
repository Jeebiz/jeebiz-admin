/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 认证授权日志信息表Model
 */
@Alias("AuthzLogModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzLogModel extends PaginationModel<AuthzLogModel> {
	
	/**
	 * 日志ID
	 */
	private String id;
	/**
	 * 认证授权对象ID
	 */
	private String userId;
	/**
	 * 认证授权人
	 */
	private String userName;
	/**
	 * 认证授权类型（login:登录认证、logout:会话注销）
	 */
	private String opt;
	/**
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、OpenID、SMAL等
	 */
	private String protocol;
	/**
	 * 负责此次认证授权的realm名称
	 */
	private String realm;
	/**
	 * 日志级别：（debug:调试、info:信息、warn:警告、error:错误、fetal:严重错误）
	 */
	private String level;
	/**
	 * 认证请求来源IP地址
	 */
	private String addr;
	/**
	 * 认证授权结果：（fail:失败、success:成功）
	 */
	private String status;
	/**
	 * 认证授权请求信息
	 */
	private String msg;
	/**
	 * 认证授权异常信息
	 */
	private String exception;
	/**
	 * 认证授权发生时间
	 */
	private String timestamp;
	/**
	 * 认证授权起始时间
	 */
	private String begintime;
	/**
	 * 认证授权结束时间
	 */
	private String endtime;
	/**
	 * 模糊搜索关键字
	 */
	private String keywords;
}
