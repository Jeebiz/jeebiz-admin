/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.service;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthzFeatureOptService extends IBaseService<AuthzFeatureOptModel>{

	public List<AuthzFeatureOptModel> getFeatureOpts();
	
	public List<AuthzFeatureOptModel> getFeatureOptList(String featureId, boolean visible);
	
	public int getOptCountByName(String name, String featureId, String optId);
	
}
