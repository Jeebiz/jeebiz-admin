/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.authz.org.dao.entities.AuthzOrganizationModel;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface AuthzOrganizationMapper extends BaseMapper<AuthzOrganizationModel> {

	int getRootCount();
	
	int getDeptCount(@Param("id")String id);
	
	List<AuthzOrganizationModel> getOrgList();
}