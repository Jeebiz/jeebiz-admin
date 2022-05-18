/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.setup.event;

import org.springframework.biz.context.event.EnhancedEvent;

import io.hiwepy.admin.extras.logbiz.dao.entities.BizLogEntity;

/**
 * 功能操作发生事件：用于记录功能操作日志
 */
@SuppressWarnings("serial")
public class BizLogEvent extends EnhancedEvent<BizLogEntity> {
	
	public BizLogEvent(Object source, BizLogEntity bind) {
		super(source, bind);
	}
	
}
