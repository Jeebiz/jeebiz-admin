/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.logbiz.dao.entities.BizExcpModel;
import net.jeebiz.admin.extras.logbiz.setup.event.BizExcpEvent;

@Component
public class BizExcpEventListener implements ApplicationListener<BizExcpEvent> {

	@Override
	public void onApplicationEvent(BizExcpEvent event) {
		
		
		BizExcpModel excpModel = event.getBind();
		/*
		ThreadContext.put("module", authzLog.module());
		ThreadContext.put("biz", authzLog.business());
		ThreadContext.put("opt", authzLog.opt().getKey());
		ThreadContext.put("userId", "01");
		
		// 记录请求日志
		Log4jUtils.error(Constants.bizExcpMarker, "业务操作异常：" + ex.getMessage(), ex);
		*/
		
	}

}
