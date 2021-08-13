/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service;

import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.boot.api.service.IBaseService;

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
	
	public int getStaffCount(String id);
	
}
