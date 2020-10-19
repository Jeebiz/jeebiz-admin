/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.service;

import java.util.List;

import net.jeebiz.admin.authz.org.dao.entities.AuthzOrganizationModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthzOrganizationService extends IBaseService<AuthzOrganizationModel> {
	
	public int getRootCount();

	public int getDeptCount(String id);

	public List<AuthzOrganizationModel> getOrgList();
	
}
