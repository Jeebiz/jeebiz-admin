/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.authz.org.dao.IAuthzDepartmentDao;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzDepartmentService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzDepartmentServiceImpl extends BaseServiceImpl<AuthzDepartmentModel, IAuthzDepartmentDao> implements IAuthzDepartmentService{

	@Override
	public int getCountByCode(String code, String orgId, String deptId) {
		return getDao().getDeptCountByCode(code, orgId, deptId);
	}

	@Override
	public int getCountByName(String name, String orgId, String deptId) {
		return getDao().getDeptCountByName(name, orgId, deptId);
	}

	@Override
	public int getStaffCount(String id) {
		return getDao().getStaffCount(id);
	}
	
}
