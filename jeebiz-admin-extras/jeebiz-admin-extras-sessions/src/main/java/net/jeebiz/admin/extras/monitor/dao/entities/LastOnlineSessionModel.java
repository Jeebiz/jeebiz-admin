/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package net.jeebiz.admin.extras.monitor.dao.entities;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

/**
 * 在线用户最后一次在线信息() 此表对于分析活跃用户有用
 */
@Alias("LastOnlineSessionModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class LastOnlineSessionModel extends PaginationEntity<LastOnlineSessionModel> {
	/**
	 * 回话记录ID
	 */
	private String id;
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

	public void incLoginCount() {
		setLoginCount(getLoginCount() + 1);
	}

	public void incTotalOnlineTime() {
		long onlineTime = getLastStopTimestamp().getTime() - getLastLoginTimestamp().getTime();
		setTotalOnlineTime(getTotalOnlineTime() + onlineTime / 1000);
	}

}
