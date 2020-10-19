/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.event;

import org.springframework.biz.context.event.EnhancedEvent;

import net.jeebiz.admin.extras.logbiz.dao.entities.BizLogModel;

/**
 * 功能操作发生事件：用于记录功能操作日志
 */
@SuppressWarnings("serial")
public class BizLogEvent extends EnhancedEvent<BizLogModel> {
	
	public BizLogEvent(Object source, BizLogModel bind) {
		super(source, bind);
	}
	
}
