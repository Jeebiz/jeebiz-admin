/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.shiro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.biz.utils.WebUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import lombok.extern.slf4j.Slf4j;
import net.bull.javamelody.internal.common.LOG;
import net.jeebiz.boot.api.ApiRestResponse;

/**
 * Google 授权 (authorization)过滤器
 */
@Slf4j
public class GoogleAuthorizationFilter extends AuthorizationFilter {

	/**
	 * HTTP Authorization Param, equal to <code>accessToken</code>
	 */
	public static final String AUTHORIZATION_PARAM = "accessToken";
	private String authorizationParamName = AUTHORIZATION_PARAM;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		
		// Step 1、生成无状态Token 
		
		
		try {
			AuthenticationToken token = createGoogleToken(request, response);
			//Step 2、委托给Realm进行登录  
			Subject subject = getSubject(request, response);
			subject.login(token);
			return true;
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		
		String mString = "Attempting to access a path which requires authentication. ";
		if (log.isTraceEnabled()) { 
			log.trace(mString);
		}
		
		WebUtils.getNativeResponse(response, HttpServletResponse.class).setStatus(HttpStatus.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		JSONObject.writeJSONString(response.getWriter(), ApiRestResponse.fail(mString));
		
		return false;
		
	}
	
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		return false;
	}

	protected AuthenticationToken createGoogleToken(ServletRequest request, ServletResponse response) throws Exception {
		
		HttpServletRequest httpRequest = WebUtils.getNativeRequest(request, HttpServletRequest.class);
		String host = WebUtils.getRemoteAddr(httpRequest);
		
		String idTokenString = this.obtainAccessToken(httpRequest);

		if (idTokenString == null) {
			idTokenString = "";
		}

		idTokenString = idTokenString.trim();
		
		if(!StringUtils.hasText(idTokenString)) {
		}
		
		String CLIENt_id = "";
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder( new NetHttpTransport(), new GsonFactory())
		    // Specify the CLIENt_id of the app that accesses the backend:
		    .setAudience(Collections.singletonList(CLIENt_id))
		    // Or, if multiple clients access the backend:
		    //.setAudience(Arrays.asList(CLIENt_id_1, CLIENt_id_2, CLIENt_id_3))
		    .build();

		// (Receive idTokenString by HTTPS POST)

		GoogleIdToken idToken = verifier.verify(idTokenString);
		if (idToken != null) {
		  

		} else {
		  System.out.println("Invalid ID token.");
		}
		
		GoogleAuthenticationToken token = new GoogleAuthenticationToken(idToken, idTokenString, host);
		
		return token;
	}

	protected String obtainAccessToken(HttpServletRequest request) {
		// 从参数中获取token
		String token = request.getParameter(getAuthorizationParamName());
		return token;
	}
	 

	public String getAuthorizationParamName() {
		return authorizationParamName;
	}

	public void setAuthorizationParamName(String authorizationParamName) {
		this.authorizationParamName = authorizationParamName;
	}
}
