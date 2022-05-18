/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.setup.shiro.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.RemoteAddrUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.filter.authc.listener.LoginListener;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.hiwepy.boot.api.Constants;
import io.hiwepy.boot.api.annotation.BusinessType;
import io.hiwepy.boot.api.sequence.Sequence;

/**
 * 登录认证日志记录：
 * 更多的参数信息可参见{@link org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor}
 */
@Component
@Slf4j
public class ShiroAuthzLoginListener implements LoginListener {

	@Autowired
	protected Sequence sequence;
	
	@Override
	public void onSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) {
		
		ShiroPrincipal principal = (ShiroPrincipal) subject.getPrincipal();

		MDC.put("opt", BusinessType.LOGIN.getKey());
		MDC.put("userId", principal.getUserid());
		MDC.put("status", Constants.RT_SUCCESS);
		
		recordRequest(WebUtils.toHttp(request));
		
		log.info(Constants.authzMarker, String.format("账户:%s登录成功.", principal.getUsername()));

	}
	
	@Override
	public void onFailure(AuthenticationToken token, Exception ex, ServletRequest request,
			ServletResponse response) {
		
		String userid = token.getPrincipal().toString();
		MDC.put("opt", BusinessType.LOGIN.getKey());
		MDC.put("userId", userid);
		MDC.put("status", Constants.RT_ERROR);
		
		recordRequest(WebUtils.toHttp(request));

		log.error(Constants.authzMarker, String.format("账户:%s登录失败.", userid), ExceptionUtils.getRootCause(ex));
		
	}

	protected void recordRequest(HttpServletRequest request) {

		MDC.put("requestId", sequence.nextId().toString());
		MDC.put("requestURL", request.getRequestURL().toString());
		MDC.put("requestURI", request.getRequestURI());
		MDC.put("queryString", request.getQueryString());
		MDC.put("remoteAddr", RemoteAddrUtils.getRemoteAddr(request));
		MDC.put("remoteHost", request.getRemoteHost());
		MDC.put("remotePort", String.valueOf(request.getRemotePort()));
		MDC.put("localAddr", request.getLocalAddr());
		MDC.put("localName", request.getLocalName());

	}
	
}
