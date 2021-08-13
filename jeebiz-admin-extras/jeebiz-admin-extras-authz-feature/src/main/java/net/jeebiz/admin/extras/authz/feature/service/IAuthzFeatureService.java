/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.service;

import java.util.List;

import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthzFeatureService extends IBaseService<AuthzFeatureModel>{

	public List<AuthzFeatureModel> getFeatureList();
	
}
