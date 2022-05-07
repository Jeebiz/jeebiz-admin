/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.setup.event;

import org.springframework.biz.context.event.EnhancedEvent;

import io.hiwepy.admin.extras.logbiz.dao.entities.LoginLogModel;

/**
 * 认证行为发生事件：用于记录认证日志
 */
@SuppressWarnings("serial")
public class AuthzLogEvent extends EnhancedEvent<LoginLogModel> {
	
	public AuthzLogEvent(Object source, LoginLogModel bind) {
		super(source, bind);
	}
	
}
