/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.authz.org.dao.IAuthzTeamDao;
import net.jeebiz.admin.authz.org.dao.entities.AuthzTeamModel;
import net.jeebiz.admin.authz.org.service.IAuthzTeamService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzTeamServiceImpl extends BaseServiceImpl<AuthzTeamModel, IAuthzTeamDao> implements IAuthzTeamService{

	@Override
	public int getTeamCountByName(String name, String deptId, String teamId) {
		return getDao().getTeamCountByName(name, deptId, teamId);
	}

	@Override
	public int getStaffCount(String id) {
		return getDao().getStaffCount(id);
	}
	
}