/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzOrganizationDao extends BaseDao<AuthzOrganizationModel> {

	public int getRootCount();
	
	public int getDeptCount(@Param("id")String id);
}
