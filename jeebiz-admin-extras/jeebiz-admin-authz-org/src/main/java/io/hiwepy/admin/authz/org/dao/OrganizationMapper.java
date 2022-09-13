/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.authz.org.dao.entities.OrganizationEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface OrganizationMapper extends BaseMapper<OrganizationEntity> {

	int getRootCount();
	
	int getDeptCount(@Param("id")String id);
	
	List<OrganizationEntity> getOrgList();
}
