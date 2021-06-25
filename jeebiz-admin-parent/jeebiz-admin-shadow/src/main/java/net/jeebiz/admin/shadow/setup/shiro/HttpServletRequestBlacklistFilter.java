/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.shiro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.servlet.http.HttpStatus;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.biz.utils.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;

import net.jeebiz.admin.api.BizRedisKey;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

public class HttpServletRequestBlacklistFilter extends AccessControlFilter {

    private final RedisOperationTemplate redisOperationTemplate;
	private final MessageSource messageSource;

	public HttpServletRequestBlacklistFilter(RedisOperationTemplate redisOperationTemplate,
			MessageSource messageSource) {
		super();
		this.redisOperationTemplate = redisOperationTemplate;
		this.messageSource = messageSource;
	}

	@Override
	protected void onFilterConfigSet() throws Exception {
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return true;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}
	
	@Override
	public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		HttpServletRequest originalRequest = WebUtils.getHttpRequest(request);
		HttpServletResponse originalResponse = WebUtils.getHttpResponse(response);
    	
        String devID = originalRequest.getHeader(XHeaders.X_DEVICE_IMEI);
        if (!StringUtils.hasText(devID)) {
            String message = getMessageSource().getMessage("app.blacklist.device.required", null, LocaleContextHolder.getLocale());
        	if (WebUtils.isAjaxResponse(request)) {
        		originalResponse.setStatus(HttpStatus.SC_OK);
        		originalResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        		JSONObject.writeJSONString(originalResponse.getWriter(), ApiRestResponse.fail(20017, message));
    		} else {
    			originalResponse.sendError(HttpStatus.SC_FORBIDDEN, message);
    		}
        	return;
        }
        
        String appBlacklistKey = BizRedisKey.APP_BLACKLIST.getFunction().apply("device", null);
        Boolean isMemeber =  getRedisOperationTemplate().sHasKey(appBlacklistKey, devID);
        if (isMemeber){
            String message = getMessageSource().getMessage("app.blacklist.device.banned", null, LocaleContextHolder.getLocale());
        	if (WebUtils.isAjaxResponse(request)) {
        		originalResponse.setStatus(HttpStatus.SC_OK);
        		originalResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        		JSONObject.writeJSONString(originalResponse.getWriter(), ApiRestResponse.fail(20017, message));
    		} else {
    			originalResponse.sendError(HttpStatus.SC_FORBIDDEN, message);
    		}
        	return;
        }
        super.doFilterInternal(request, response, chain);
    }

    public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}

    public MessageSource getMessageSource() {
		return messageSource;
	}

}
