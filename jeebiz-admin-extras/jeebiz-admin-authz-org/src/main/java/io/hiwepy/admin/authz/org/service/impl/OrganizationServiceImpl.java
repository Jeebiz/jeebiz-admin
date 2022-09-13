/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.OrganizationMapper;
import io.hiwepy.admin.authz.org.dao.entities.OrganizationEntity;
import io.hiwepy.admin.authz.org.service.IOrganizationService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationMapper, OrganizationEntity> implements IOrganizationService{

	@Override
	public int getRootCount() {
		return getBaseMapper().getRootCount();
	}
	
	@Override
	public int getDeptCount(String id) {
		return getBaseMapper().getDeptCount(id);
	}

	@Override
	public List<OrganizationEntity> getOrgList() {
		return getBaseMapper().getOrgList();
	}
	
}
