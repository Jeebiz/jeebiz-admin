/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.service;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginStatusModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthzLoginService extends IBaseService<AuthzLoginModel>{
	
	RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException;
	
	/**
	 * 根据用户id和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @param password : 密码，可不填
	 * @return 用户账号状态信息
	 */
	AuthzLoginStatusModel getAccountStatus(String account, String password);

	/***
	 *  根据用户id和密码查询用户信息
	 * @param account : 用户名
	 * @param password : 密码，可不填
	 * @return 用户登录信息
	 */
	AuthzLoginModel getAccount(String account, String password);

	/***
	 * 根据用户id无密码查询用户信息；用于单点登录
	 * @param account : 用户名
	 * @return 用户登录信息
	 */
	AuthzLoginModel getAccountWithoutPwd(String account);

}
