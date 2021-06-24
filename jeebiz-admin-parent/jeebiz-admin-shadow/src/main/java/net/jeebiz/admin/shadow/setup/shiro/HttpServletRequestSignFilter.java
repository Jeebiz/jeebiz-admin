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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.shadow.setup.CommonProperteis;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.utils.SignUtils;
import net.jeebiz.boot.api.utils.StringUtils;

/**
 * 1、参考资料：
 * https://www.haoyizebo.com/posts/876ed1e8/
 * https://www.cnblogs.com/grimm/p/14031241.html
 */
@Slf4j
public class HttpServletRequestSignFilter extends AccessControlFilter {

	private static final AntPathMatcher pathMathcer = new AntPathMatcher();
	
	/**
	 * HTTP Authorization header, equal to <code>X-Authorization</code>
	 */
	public static final String AUTHORIZATION_HEADER = "X-Authorization";
	public static final String AUTHORIZATION_PARAM = "token";

	private String authorizationHeaderName = AUTHORIZATION_HEADER;
	private String authorizationParamName = AUTHORIZATION_PARAM;
	private String authorizationCookieName = AUTHORIZATION_PARAM;
	
	/**
	 * HTTP Sign header, equal to <code>X-Sign</code>
	 */
	public static final String SIGN_HEADER = "X-Sign";
	public static final String SIGN_PARAM = "sign";

	private String signHeaderName = SIGN_HEADER;
	private String signParamName = SIGN_PARAM;
	private String signCookieName = SIGN_PARAM;

	@Autowired
	private CommonProperteis commonProperteis;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ObjectMapper objectMapper;
    
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
		
		// 1、Sign 未开启
		if(!commonProperteis.isSignOpen()) {
			super.doFilterInternal(request, response, chain);
			return;
		}
		// 2、忽略部分请求
		String path = originalRequest.getRequestURI();
		log.info("Path：{}", path);
		log.info("WhiteList：{}", commonProperteis.getWhiteList());
		if(StringUtils.hasText(commonProperteis.getWhiteList())) {
			for (String pattern : StringUtils.tokenizeToStringArray(commonProperteis.getWhiteList())) {
				if(pathMathcer.match(pattern, path)) {
					super.doFilterInternal(request, response, chain);
					return;
				}
			}
		}

		originalRequest.setAttribute("startTime", System.currentTimeMillis());
    	
		// 提取 Token
		String token = obtainToken(originalRequest);
		
        // 2.1、提取 Sign
 		String sign = obtainSign(originalRequest);
 		// 2.2、返回1403状态码和提示信息
 		if (!StringUtils.hasText(sign)) {
 			processMiss(originalRequest, originalResponse);
	    	return;
 		}
	    
 		// 3、进行签名逻辑处理
        try {
        	
        	String appId = originalRequest.getHeader(XHeaders.X_APP_ID);
        	String appChannel = originalRequest.getHeader(XHeaders.X_APP_CHANNEL);
        	String appVersion = originalRequest.getHeader(XHeaders.X_APP_VERSION);
        	
    	    // 3.2、请求参数，post从请求里获取请求体
        	String requestBodyStr = originalRequest.getMethod().equals(HttpMethod.POST.name()) ? objectMapper.readValue(originalRequest.getReader(), String.class) : null;
		    // 3.3、对参数进行签名验证
            if(!SignUtils.verify(token, appId, appVersion, appChannel, commonProperteis.getFixedSecret(), 
            		sign, originalRequest.getParameterMap(), requestBodyStr)) {
            	processFail(originalRequest, originalResponse);
                return;
            }
            
        } catch (Exception e){
            processError(originalRequest, originalResponse, e.getMessage());
            return;
        }
        processFilter(originalRequest, originalResponse, chain);
	}

	protected String obtainToken(HttpServletRequest request) {
		//	从header中获取token
		String token = request.getHeader(getAuthorizationHeaderName());
		//	如果header中不存在token，则从参数中获取token
		if (StringUtils.isEmpty(token)) {
			return request.getParameter(getAuthorizationParamName());
		}
		return token;
	}
	
	protected String obtainSign(HttpServletRequest request) {
		//	从header中获取sign
		String token = request.getHeader(getSignHeaderName());
		//	如果header中不存在sign，则从参数中获取sign
		if (StringUtils.isEmpty(token)) {
			return request.getParameter(getSignParamName());
		}
		return token;
	}
    
    protected void processFilter(ServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{
    	try {
			super.doFilterInternal(request, response, chain);
		} finally {
			Long startTime = (Long) request.getAttribute("startTime");
            if (startTime != null){
                long executeTime = (System.currentTimeMillis() - startTime);
                log.info("耗时：{}ms" , executeTime);
                log.info("状态码：{}" , response.getStatus());
            }
		}
    }
    
    protected void processMiss(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String mString = messageSource.getMessage("request.signature.required", null, LocaleContextHolder.getLocale());
    	if (WebUtils.isAjaxResponse(request)) {
    		response.setStatus(HttpStatus.SC_OK);
    		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    		JSONObject.writeJSONString(response.getWriter(), ApiRestResponse.fail(1403, mString));
		} else {
			response.sendError(HttpStatus.SC_FORBIDDEN, mString);
		}
    }
    
    protected void processFail(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String mString = messageSource.getMessage("request.signature.unmatch", null, LocaleContextHolder.getLocale());
    	if (WebUtils.isAjaxResponse(request)) {
    		response.setStatus(HttpStatus.SC_OK);
    		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    		JSONObject.writeJSONString(response.getWriter(), ApiRestResponse.fail(1403, mString));
		} else {
			response.sendError(HttpStatus.SC_FORBIDDEN, mString);
		}
    }
 
    protected void processError(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
    	
    	log.error(message);

    	String mString = messageSource.getMessage("request.signature.incorrect", null, LocaleContextHolder.getLocale());
    	if (WebUtils.isAjaxResponse(request)) {
    		response.setStatus(HttpStatus.SC_OK);
    		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    		JSONObject.writeJSONString(response.getWriter(), ApiRestResponse.error(1403, mString));
		} else {
			response.sendError(HttpStatus.SC_FORBIDDEN, mString);
		}
		
    }

	public MessageSource getMessageSource() {
		return messageSource;
	}
	

	public String getAuthorizationHeaderName() {
		return authorizationHeaderName;
	}

	public void setAuthorizationHeaderName(String authorizationHeaderName) {
		this.authorizationHeaderName = authorizationHeaderName;
	}

	public String getAuthorizationParamName() {
		return authorizationParamName;
	}

	public void setAuthorizationParamName(String authorizationParamName) {
		this.authorizationParamName = authorizationParamName;
	}

	public String getAuthorizationCookieName() {
		return authorizationCookieName;
	}

	public void setAuthorizationCookieName(String authorizationCookieName) {
		this.authorizationCookieName = authorizationCookieName;
	}
	
	public String getSignHeaderName() {
		return signHeaderName;
	}

	public void setSignHeaderName(String signHeaderName) {
		this.signHeaderName = signHeaderName;
	}

	public String getSignParamName() {
		return signParamName;
	}

	public void setSignParamName(String signParamName) {
		this.signParamName = signParamName;
	}

	public String getSignCookieName() {
		return signCookieName;
	}

	public void setSignCookieName(String signCookieName) {
		this.signCookieName = signCookieName;
	}

}
