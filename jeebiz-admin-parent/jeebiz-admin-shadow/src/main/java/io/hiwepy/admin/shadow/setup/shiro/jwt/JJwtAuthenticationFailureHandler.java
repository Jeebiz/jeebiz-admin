/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.shadow.setup.shiro.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authc.AuthcResponse;
import org.apache.shiro.biz.authc.AuthcResponseCode;
import org.apache.shiro.biz.authc.AuthenticationFailureHandler;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.apache.shiro.biz.utils.WebUtils;
import org.apache.shiro.biz.web.servlet.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.github.hiwepy.jwt.exception.ExpiredJwtException;
import com.github.hiwepy.jwt.exception.IncorrectJwtException;
import com.github.hiwepy.jwt.exception.InvalidJwtToken;
import com.github.hiwepy.jwt.exception.NotObtainedJwtException;

public class JJwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger LOG = LoggerFactory.getLogger(JJwtAuthenticationFailureHandler.class);
	
	@Override
	public boolean supports(AuthenticationException ex) {
		Throwable cause = Objects.isNull(ex.getCause()) ? ex : ex.getCause();
		return SubjectUtils.isAssignableFrom(cause.getClass(), IncorrectJwtException.class,
				ExpiredJwtException.class, InvalidJwtToken.class, NotObtainedJwtException.class);
	}

	@Override
	public void onAuthenticationFailure(AuthenticationToken token, ServletRequest request, ServletResponse response,
			AuthenticationException ex) {
		
		if(LOG.isDebugEnabled()) {
			LOG.debug(ExceptionUtils.getRootCauseMessage(ex));
		}
		
		try {
			
			Throwable cause = Objects.isNull(ex.getCause()) ? ex : ex.getCause();
					
			WebUtils.toHttp(response).setStatus(HttpStatus.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			
			// Jwt过期
			if (cause instanceof ExpiredJwtException) {
				JSONObject.writeJSONString(response.getWriter(), AuthcResponse.error(AuthcResponseCode.SC_AUTHZ_TOKEN_EXPIRED.getCode(),"Jwt已过期"));
			} 
			// Jwt错误
			else if (cause instanceof IncorrectJwtException) {
				JSONObject.writeJSONString(response.getWriter(), AuthcResponse.error(AuthcResponseCode.SC_AUTHZ_TOKEN_INCORRECT.getCode(),
						"Jwt错误"));
			} 
			// Jwt无效
			else if (cause instanceof InvalidJwtToken) {
				JSONObject.writeJSONString(response.getWriter(), AuthcResponse.error(AuthcResponseCode.SC_AUTHZ_TOKEN_INVALID.getCode(),
						"Jwt无效"));
			}
			// Jwt缺失
			else if (cause instanceof NotObtainedJwtException) {
				JSONObject.writeJSONString(response.getWriter(), AuthcResponse.error(AuthcResponseCode.SC_AUTHZ_TOKEN_REQUIRED.getCode(),
						"Jwt缺失"));
			} else {
				JSONObject.writeJSONString(response.getWriter(), AuthcResponse.error(AuthcResponseCode.SC_AUTHC_FAIL.getCode(),
						"鉴权失败，请重新登录"));
			}
		} catch (NoSuchMessageException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		
	}

}
