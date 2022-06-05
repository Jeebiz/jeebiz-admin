/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.tree.setup.event.listener;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.Log4jUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.hiwepy.admin.extras.tree.dao.entities.AuthzLogEntity;
import io.hiwepy.admin.extras.tree.setup.Constants;
import io.hiwepy.admin.extras.tree.setup.event.AuthzLogEvent;

@Component
public class AuthzLogEventListener implements ApplicationListener<AuthzLogEvent> {

	
	@Override
	public void onApplicationEvent(AuthzLogEvent event) {
		
		AuthzLogEntity authzLog = event.getBind();
		
		//ThreadContext.put("module", authzLog.module());
		//ThreadContext.put("biz", authzLog.business());
		//ThreadContext.put("opt", authzLog.opt().getKey());
		ThreadContext.put("userId", "01");
		
		// 记录请求日志
		Log4jUtils.instance("Authz-Log").error(Constants.authzLogMarker, "业务操作异常：");
		
	}

}
