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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;

import net.jeebiz.admin.api.BizRedisKey;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

// @Component
public class HttpServletRequestBlacklistFilter extends AccessControlFilter {

    @Autowired
    private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private MessageSource messageSource;

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
    	
        String devID = originalRequest.getHeader("equipNum");

        String appBlacklistKey = BizRedisKey.APP_BLACKLIST.getFunction().apply("device", null);

        // KDING_SET_DEVICE_BLACK_LIST
        Boolean isMemeber =  getRedisOperationTemplate().sHasKey(appBlacklistKey, devID);
        if (isMemeber){
        	//app.blacklist.device
            String message = getMessageSource().getMessage("app.blacklist.device", null, LocaleContextHolder.getLocale());
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
