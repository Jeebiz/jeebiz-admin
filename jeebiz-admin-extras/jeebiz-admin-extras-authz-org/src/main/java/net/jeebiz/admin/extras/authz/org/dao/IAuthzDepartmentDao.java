/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzDepartmentModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzDepartmentDao extends BaseDao<AuthzDepartmentModel> {

	/**
	 * 根据编码获取记录数
	 * @return
	 */
	public int getDeptCountByCode(@Param("code") String code, @Param("orgId") String orgId, @Param("deptId") String deptId);
	
	/**
	 * 根据名称获取记录数
	 * @return
	 */
	public int getDeptCountByName(@Param("name") String name, @Param("orgId") String orgId, @Param("deptId") String deptId);

	public int getStaffCount(@Param("id") String id);
	
}
