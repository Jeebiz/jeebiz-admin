/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.service;

import net.jeebiz.admin.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.boot.api.service.IBaseMapperService;

public interface IAuthzDepartmentService extends IBaseMapperService<AuthzDepartmentModel> {
	
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
