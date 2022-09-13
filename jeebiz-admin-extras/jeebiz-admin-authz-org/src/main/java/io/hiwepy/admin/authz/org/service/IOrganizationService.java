/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service;

import java.util.List;

import io.hiwepy.admin.authz.org.dao.entities.OrganizationEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IOrganizationService extends IBaseService<OrganizationEntity> {
	
	int getRootCount();

	int getDeptCount(String id);

	List<OrganizationEntity> getOrgList();
	
}
