/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service.impl;


import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.shadow.dao.IAuthzLoginDao;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.admin.shadow.service.IAuthzLoginService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzLoginServiceImpl extends BaseServiceImpl<AuthzLoginModel, IAuthzLoginDao>
		implements IAuthzLoginService {

	public static final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";
	
	@Override
	public Map<String, String> getAccountStatus(String username, String password) {
		return getDao().getAccountStatus(username, password);
	}

	@Override
	public AuthzLoginModel getAccount(String username, String password) {
		return getDao().getAccount(username, password);
	}
	
	@Override
	public AuthzLoginModel getAccountWithoutPwd(String username) {
		return getDao().getAccountWithoutPwd(username);
	}
	
	@Override
	public RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException {
		
		/*KeyPair keyPair = SecretKeyUtils.genKeyPair("RSA");
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		SubjectUtils.getSession().setAttribute(PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);
		
		return publicKey;*/
		
		return null;
	}
}
