/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.core.setup.shiro;

import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spring.boot.utils.RemoteAddrUtils;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

public class Log4j2MDCRequestFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		ThreadContext.put("uuid", UUID.randomUUID().toString()); // Add the fishtag;
		ThreadContext.put("requestURL", httpRequest.getRequestURL().toString());
		ThreadContext.put("requestURI", httpRequest.getRequestURI());
		ThreadContext.put("queryString", httpRequest.getQueryString());
		ThreadContext.put("remoteAddr", RemoteAddrUtils.getRemoteAddr(httpRequest));
		ThreadContext.put("remoteHost", request.getRemoteHost());
		ThreadContext.put("remotePort", String.valueOf(request.getRemotePort()));
		ThreadContext.put("localAddr", request.getLocalAddr());
		ThreadContext.put("localName", request.getLocalName());
		
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}

}
