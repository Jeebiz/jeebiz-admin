/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.service;

import java.util.List;

import net.jeebiz.boot.api.service.BaseService;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureOptModel;

public interface IAuthzFeatureOptService extends BaseService<AuthzFeatureOptModel>{

	public List<AuthzFeatureOptModel> getFeatureOpts();
	
	public List<AuthzFeatureOptModel> getFeatureOptList(String featureId, boolean visible);
	
	public int getOptCountByName(String name, String featureId, String optId);
	
}
