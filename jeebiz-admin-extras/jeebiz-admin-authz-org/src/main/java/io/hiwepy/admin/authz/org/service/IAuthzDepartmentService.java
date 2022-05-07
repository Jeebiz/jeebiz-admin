/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service;

import io.hiwepy.admin.authz.org.dao.entities.AuthzDepartmentModel;
import io.hiwepy.boot.api.service.IBaseService;

public interface IAuthzDepartmentService extends IBaseService<AuthzDepartmentModel> {
	
	/**
	 * 根据编码获取记录数
	 * @return
	 */
	public int getCountByCode(String code, String orgId, String deptId);
	
	/**
	 * 根据名称获取记录数
	 * @return
	 */
	public int getCountByName(String name, String orgId, String deptId);
	
	/**
	 * 获取部门下成员梳理
	 * @param id
	 * @return
	 */
	public int getStaffCount(String id);
	
}
