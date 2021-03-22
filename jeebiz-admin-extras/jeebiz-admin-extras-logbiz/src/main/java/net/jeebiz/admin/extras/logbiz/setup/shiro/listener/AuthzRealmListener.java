/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.shiro.listener;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.realm.AuthorizingRealmListener;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthzRealmListener implements AuthorizingRealmListener {

	private Logger LOG = LoggerFactory.getLogger(AuthzRealmListener.class);

	@Override
	public void onFailure(AuthorizingRealm realm, AuthenticationToken token, AuthenticationException ex) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Authentication Fail With Realm: {} and {}", realm.getName(), token);
		}
	}

	@Override
	public void onSuccess(AuthorizingRealm realm, AuthenticationInfo info) {
		if(LOG.isDebugEnabled()) {
			LOG.debug("Authentication Success With Realm : {}.", realm.getName());
		}
		
	}

}
