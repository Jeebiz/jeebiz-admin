/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.authz.rbac0.dao.IAuthzUserProfileDao;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserProfileModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserProfileService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzUserProfileServiceImpl extends BaseServiceImpl<AuthzUserProfileModel, IAuthzUserProfileDao> implements IAuthzUserProfileService {

	@Override
	public AuthzUserProfileModel getProfile(String uid) {
		return getDao().getProfile(uid);
	}
	
	@Override
	public int getCountByPhone(String phone, String origin) {
		return getDao().getCountByPhone(phone, origin);
	}

	@Override
	public int getCountByEmail(String email, String origin) {
		return getDao().getCountByEmail(email, origin);
	}

	
}
