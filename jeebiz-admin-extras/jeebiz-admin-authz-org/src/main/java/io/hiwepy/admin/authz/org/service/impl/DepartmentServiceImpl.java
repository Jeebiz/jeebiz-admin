/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.DepartmentMapper;
import io.hiwepy.admin.authz.org.dao.entities.DepartmentEntity;
import io.hiwepy.admin.authz.org.service.IDepartmentService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl< DepartmentMapper, DepartmentEntity> implements IDepartmentService{

	@Override
	public int getCountByCode(String code, String orgId, String deptId) {
		return getBaseMapper().getDeptCountByCode(code, orgId, deptId);
	}

	@Override
	public int getCountByName(String name, String orgId, String deptId) {
		return getBaseMapper().getDeptCountByName(name, orgId, deptId);
	}

	@Override
	public int getStaffCount(String id) {
		return getBaseMapper().getStaffCount(id);
	}
	
}
