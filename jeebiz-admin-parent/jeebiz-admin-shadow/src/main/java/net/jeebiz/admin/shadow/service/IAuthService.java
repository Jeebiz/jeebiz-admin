/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import net.jeebiz.admin.shadow.bo.AuthBO;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthService extends IBaseService<AuthzLoginModel>{
	
	RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException;

	/**
	 * 4、登录后的操作
	 * @param <T>
	 * @param authBO
	 */
	<T> void afterLogin(AuthBO<T> authBO);

}
