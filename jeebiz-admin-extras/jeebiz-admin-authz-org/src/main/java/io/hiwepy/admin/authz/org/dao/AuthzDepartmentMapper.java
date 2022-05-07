/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.authz.org.dao.entities.AuthzDepartmentModel;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface AuthzDepartmentMapper extends BaseMapper<AuthzDepartmentModel> {

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
	
	/**
	 * 获取部门下成员梳理
	 * @param id
	 * @return
	 */
	public int getStaffCount(@Param("id") String id);
	
}
