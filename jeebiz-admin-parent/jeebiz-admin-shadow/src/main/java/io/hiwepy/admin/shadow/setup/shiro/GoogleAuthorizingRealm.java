/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.shadow.setup.shiro;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class GoogleAuthorizingRealm extends AuthorizingRealm {

	@Override
	public Class<?> getAuthenticationTokenClass() {
		return GoogleAuthenticationToken.class;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		if(principals == null || principals.isEmpty()){
			return null;
		}
		
    	Set<String> permissionsSet = Stream.of("*").collect(Collectors.toSet());
    	
    	Set<String> rolesSet = Stream.of("player").collect(Collectors.toSet());
    	
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(rolesSet);
		authorizationInfo.setStringPermissions(permissionsSet);
        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		ShiroPrincipal principal = (ShiroPrincipal) token;
		/*
		 * if(!StringUtils.hasText(principal.getKdingUserId())) { throw new
		 * InvalidAccountException("Username or password is incorrect, please re-enter."
		 * ); }
		 */
		return new SimpleAuthenticationInfo(principal, null, "google");
	}

}
