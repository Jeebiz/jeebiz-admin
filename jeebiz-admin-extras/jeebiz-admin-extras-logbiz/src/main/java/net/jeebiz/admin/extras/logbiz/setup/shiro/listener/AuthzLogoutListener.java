/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.shiro.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.web.filter.authc.listener.LogoutListener;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.logbiz.setup.Constants;

/**
 * 会话注销日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
public class AuthzLogoutListener implements LogoutListener {

	private Logger LOG = LoggerFactory.getLogger(AuthzLogoutListener.class);
	
	@Override
	public void beforeLogout(Subject subject, ServletRequest request, ServletResponse response) {
		
	}
	
	@Override
	public void onFailure(Subject subject, Exception ex) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		ThreadContext.put("userid", principal.getUserid());
		ThreadContext.put("username", principal.getUsername());
		
		LOG.error(Constants.authzLogMarker, String.format("用户:%s账户注销失败.", principal.getUsername()));
		
	}

	@Override
	public void onSuccess(Subject subject, ServletRequest request, ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		//ThreadContext.put("userid", principal.getUserid());
		//ThreadContext.put("username", principal.getUsername());
		
		//LOG.info(Constants.authzLogMarker, String.format("用户:%s账户注销失败.", principal.getUsername()));
		
		
	}
	
}
