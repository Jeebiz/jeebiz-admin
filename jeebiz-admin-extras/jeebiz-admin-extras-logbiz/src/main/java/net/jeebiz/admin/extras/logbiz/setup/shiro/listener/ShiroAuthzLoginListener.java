/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.shiro.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.RemoteAddrUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.filter.authc.listener.LoginListener;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.jeebiz.boot.api.Constants;
import net.jeebiz.boot.api.annotation.BusinessType;
import net.jeebiz.boot.api.sequence.Sequence;

/**
 * 登录认证日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
public class ShiroAuthzLoginListener implements LoginListener {

	@Autowired
	protected Sequence sequence;
	
	@Override
	public void onSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();
		
		ThreadContext.put("opt", BusinessType.LOGIN.getKey());
		ThreadContext.put("userId", principal.getUserid());
		ThreadContext.put("status", Constants.RT_SUCCESS);
		
		recordRequest(WebUtils.toHttp(request));
		
		LoggerFactory.getLogger("Authz-Log").info(Constants.authzMarker, String.format("账户:%s登录成功.", principal.getUsername()));
		
		
	}
	
	@Override
	public void onFailure(AuthenticationToken token, Exception ex, ServletRequest request,
			ServletResponse response) {
		
		String userid = token.getPrincipal().toString();
		ThreadContext.put("opt", BusinessType.LOGIN.getKey());
		ThreadContext.put("userId", userid);
		ThreadContext.put("status", Constants.RT_ERROR);
		
		recordRequest(WebUtils.toHttp(request));
		
		LoggerFactory.getLogger("Authz-Log").error(Constants.authzMarker, String.format("账户:%s登录失败.", userid), ExceptionUtils.getRootCause(ex));
		
	}
	
	protected void recordRequest(HttpServletRequest request) {
		
		ThreadContext.put("requestId", sequence.nextId().toString()); 
		ThreadContext.put("requestURL", request.getRequestURL().toString());
		ThreadContext.put("requestURI", request.getRequestURI());
		ThreadContext.put("queryString", request.getQueryString());
		ThreadContext.put("remoteAddr", RemoteAddrUtils.getRemoteAddr(request));
		ThreadContext.put("remoteHost", request.getRemoteHost());
		ThreadContext.put("remotePort", String.valueOf(request.getRemotePort()));
		ThreadContext.put("localAddr", request.getLocalAddr());
		ThreadContext.put("localName", request.getLocalName());
		
	}
	
}
