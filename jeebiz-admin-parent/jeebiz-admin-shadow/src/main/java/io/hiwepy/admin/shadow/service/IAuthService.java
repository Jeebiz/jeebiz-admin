/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow.service;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.shadow.bo.AuthBO;
import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.boot.api.service.IBaseService;

public interface IAuthService extends IBaseService<UserAccountEntity> {

	RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException;

	/**
	 * 查询用户个人信息
	 * @param userId
	 * @return
	 */
	BaseMap getAuthProfile(String userId);

	/**
	 * 4、登录后的操作
	 * @param <T>
	 * @param authBO
	 */
	<T> void afterLogin(AuthBO<T> authBO);

}
