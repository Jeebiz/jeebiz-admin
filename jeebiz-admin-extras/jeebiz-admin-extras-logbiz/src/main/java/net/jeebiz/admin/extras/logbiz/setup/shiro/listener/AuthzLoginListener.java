/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.shiro.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.web.filter.authc.listener.LoginListener;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.logbiz.setup.Constants;

/**
 * 登录认证日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
public class AuthzLoginListener implements LoginListener {
	
	private Logger LOG = LoggerFactory.getLogger(AuthzLoginListener.class);
	
	@Override
	public void onFailure(AuthenticationToken token, Exception ex, ServletRequest request,
			ServletResponse response) {
		
		String userid = token.getPrincipal().toString();
		
		ThreadContext.put("userid", userid );
		
		LOG.error(Constants.authzLogMarker, String.format("账户:%s登录失败.", userid), ex);
		
	}

	@Override
	public void onSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		ThreadContext.put("userid", principal.getUserid());
		ThreadContext.put("username", principal.getUsername());
		
		
		LOG.info(Constants.authzLogMarker, String.format("账户:%s登录成功.", principal.getUserid()));
		
	}
	
	
	
}
