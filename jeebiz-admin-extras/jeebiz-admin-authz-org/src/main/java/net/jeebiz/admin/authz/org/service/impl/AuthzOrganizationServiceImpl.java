/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.authz.org.dao.IAuthzOrganizationDao;
import net.jeebiz.admin.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.admin.authz.org.service.IAuthzOrganizationService;
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

	@Override
	public List<AuthzOrganizationModel> getOrgList() {
		return getDao().getOrgList();
	}
	
}
