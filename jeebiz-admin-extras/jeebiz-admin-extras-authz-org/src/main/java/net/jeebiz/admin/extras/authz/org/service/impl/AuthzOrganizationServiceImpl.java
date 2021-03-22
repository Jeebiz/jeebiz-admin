/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.authz.org.dao.IAuthzOrganizationDao;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzOrganizationService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzOrganizationServiceImpl extends BaseServiceImpl<AuthzOrganizationModel, IAuthzOrganizationDao> implements IAuthzOrganizationService{

	@Override
	public int getRootCount() {
		return getDao().getRootCount();
	}
	
	@Override
	public int getDeptCount(String id) {
		return getDao().getDeptCount(id);
	}
	
}
