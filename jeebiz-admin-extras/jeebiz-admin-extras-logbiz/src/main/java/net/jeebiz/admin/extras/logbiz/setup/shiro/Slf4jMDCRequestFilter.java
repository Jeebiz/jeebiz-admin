/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.shiro;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.MDC;

import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.sequence.Sequence;

public class Slf4jMDCRequestFilter extends AccessControlFilter {
	
	private final Sequence sequence;
	
	public Slf4jMDCRequestFilter(Sequence sequence) {
		super();
		this.sequence = sequence;
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		
		MDC.put("requestId", StringUtils.defaultString(httpRequest.getHeader(XHeaders.X_REQUEST_ID), sequence.nextId().toString())); 
		MDC.put("requestURL", httpRequest.getRequestURL().toString());
		MDC.put("requestURI", httpRequest.getRequestURI());
		MDC.put("queryString", httpRequest.getQueryString());
		MDC.put("remoteAddr", WebUtils.getRemoteAddr(request));
		MDC.put("remoteHost", request.getRemoteHost());
		MDC.put("remotePort", String.valueOf(request.getRemotePort()));
		MDC.put("localAddr", request.getLocalAddr());
		MDC.put("localName", request.getLocalName());
		
		Enumeration<String> names = httpRequest.getHeaderNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			MDC.put("header." + key, httpRequest.getHeader(key));
		}
		
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement();
			MDC.put("param." + key, request.getParameter(key));
		}
		
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}

}
