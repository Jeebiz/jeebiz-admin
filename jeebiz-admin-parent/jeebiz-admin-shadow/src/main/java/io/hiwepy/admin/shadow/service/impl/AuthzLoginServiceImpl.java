/** 
 * Copyright (C) 2018 Jeebiz (http://dajuxiaowo.com).   
 * All Rights Reserved.  
 */
package io.hiwepy.admin.shadow.service.impl;


import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.github.hiwepy.jwt.utils.SecretKeyUtils;

import io.hiwepy.admin.shadow.dao.AuthzLoginMapper;
import io.hiwepy.admin.shadow.dao.entities.AuthzLoginModel;
import io.hiwepy.admin.shadow.dao.entities.AuthzLoginStatusModel;
import io.hiwepy.admin.shadow.service.IAuthzLoginService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class AuthzLoginServiceImpl extends BaseServiceImpl<AuthzLoginMapper, AuthzLoginModel  >
		implements IAuthzLoginService {

	public static final String PRIVATE_KEY_ATTRIBUTE_name = "privateKey";
	
	@Override
	public AuthzLoginStatusModel getAccountStatus(String username, String password) {
		return getBaseMapper().getAccountStatus(username, password);
	}

	@Override
	public AuthzLoginModel getAccount(String username, String password) {
		return getBaseMapper().getAccount(username, password);
	}
	
	@Override
	public AuthzLoginModel getAccountWithoutPwd(String username) {
		return getBaseMapper().getAccountWithoutPwd(username);
	}
	
	@Override
	public RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException {
		
		KeyPair keyPair = SecretKeyUtils.genKeyPair("RSA");
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		//SubjectUtils.getSession().setAttribute(PRIVATE_KEY_ATTRIBUTE_name, privateKey);
		
		return publicKey;
	}
}