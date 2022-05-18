/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.setup.event;

import org.springframework.biz.context.event.EnhancedEvent;

import io.hiwepy.admin.extras.logbiz.dao.entities.AuthzLogEntity;

/**
 * 认证行为发生事件：用于记录认证日志
 */
@SuppressWarnings("serial")
public class AuthzLogEvent extends EnhancedEvent<AuthzLogEntity> {
	
	public AuthzLogEvent(Object source, AuthzLogEntity bind) {
		super(source, bind);
	}
	
}
