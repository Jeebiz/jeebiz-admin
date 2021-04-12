/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.shiro;

import org.apache.shiro.biz.authc.token.DefaultAuthenticationToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

@SuppressWarnings("serial")
public class GoogleAuthenticationToken extends DefaultAuthenticationToken {
	
	private GoogleIdToken idToken;
	
	private String accessToken;
	
    public GoogleAuthenticationToken() {};

    public GoogleAuthenticationToken(GoogleIdToken idToken, String accessToken, String host) {
    	this.idToken = idToken;
    	this.accessToken = accessToken;
    	super.setHost(host);
    }
    
    public GoogleIdToken getIdToken() {
		return idToken;
	}
    
    public String getAccessToken() {
		return accessToken;
	}
    
}
