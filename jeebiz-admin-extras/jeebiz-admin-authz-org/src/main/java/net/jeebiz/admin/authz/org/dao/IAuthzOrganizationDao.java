/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzOrganizationDao extends BaseDao<AuthzOrganizationModel> {

	int getRootCount();
	
	int getDeptCount(@Param("id")String id);
	
	List<AuthzOrganizationModel> getOrgList();
}
