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

import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.shadow.bo.AuthBO;
import io.hiwepy.admin.shadow.dao.AuthMapper;
import io.hiwepy.admin.shadow.service.IAuthService;
import io.hiwepy.boot.api.dao.entities.BaseMap;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class AuthServiceImpl extends BaseServiceImpl<AuthMapper, UserAccountEntity> implements IAuthService {

	public static final String PRIVATE_KEY_ATTRIBUTE_name = "privateKey";

	@Override
	public RSAPublicKey genPublicKey(HttpServletRequest request) throws GeneralSecurityException {

		KeyPair keyPair = SecretKeyUtils.genKeyPair("RSA");
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		//SubjectUtils.getSession().setAttribute(PRIVATE_KEY_ATTRIBUTE_name, privateKey);

		return publicKey;
	}

	@Override
	public BaseMap getAuthProfile(String userId) {
		return getBaseMapper().getAuthProfile(userId);
	}

	@Override
	public <T> void afterLogin(AuthBO<T> authBO) {

	}
}
