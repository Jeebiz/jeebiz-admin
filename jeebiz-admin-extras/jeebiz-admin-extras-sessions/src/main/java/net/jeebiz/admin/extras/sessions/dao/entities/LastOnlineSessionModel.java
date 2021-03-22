/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package net.jeebiz.admin.extras.sessions.dao.entities;

import java.util.Date;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

/**
 * 在线用户最后一次在线信息() 此表对于分析活跃用户有用
 */
@SuppressWarnings("serial")
public class LastOnlineSessionModel extends PaginationModel {

	/**
	 * 最后退出时的记录uid
	 */
	private String uid;
	/** 在线的用户Id */
	private String userid;
	/** 在线的用户名称 */
	private String username;
	/** 用户主机地址 */
	private String host;
	/** 用户浏览器类型 */
	private String userAgent;
	/** 用户登录时系统IP */
	private String systemHost;
	/** 最后登录时间 */
	private Date lastLoginTimestamp;
	/** 最后退出时间 */
	private Date lastStopTimestamp;
	/** 登录次数 */
	private Integer loginCount = 0;
	/** 总的在线时长（秒为单位） */
	private Long totalOnlineTime = 0L;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getSystemHost() {
		return systemHost;
	}

	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}

	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	public Date getLastStopTimestamp() {
		return lastStopTimestamp;
	}

	public void setLastStopTimestamp(Date lastStopTimestamp) {
		this.lastStopTimestamp = lastStopTimestamp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Long getTotalOnlineTime() {
		return totalOnlineTime;
	}

	public void setTotalOnlineTime(Long totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}

	public void incLoginCount() {
		setLoginCount(getLoginCount() + 1);
	}

	public void incTotalOnlineTime() {
		long onlineTime = getLastStopTimestamp().getTime() - getLastLoginTimestamp().getTime();
		setTotalOnlineTime(getTotalOnlineTime() + onlineTime / 1000);
	}

}
