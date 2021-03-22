/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.event;

import org.springframework.biz.context.event.EnhancedEvent;

import net.jeebiz.admin.extras.logbiz.dao.entities.BizExcpModel;

/**
 * 系统异常发生事件：用于记录系统异常日志
 */
@SuppressWarnings("serial")
public class BizExcpEvent extends EnhancedEvent<BizExcpModel> {
	
	public BizExcpEvent(Object source, BizExcpModel bind) {
		super(source, bind);
	}
	
}
