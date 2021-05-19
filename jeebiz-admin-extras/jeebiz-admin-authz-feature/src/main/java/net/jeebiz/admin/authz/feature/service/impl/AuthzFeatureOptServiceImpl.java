/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.authz.feature.dao.IAuthzFeatureOptDao;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.service.IAuthzFeatureOptService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class AuthzFeatureOptServiceImpl extends BaseMapperServiceImpl<AuthzFeatureOptModel, IAuthzFeatureOptDao> implements IAuthzFeatureOptService {

	@Override
	public AuthzFeatureOptModel getFeatureOpt(String id) {
		return getBaseMapper().getFeatureOpt(id);
	}
	
	@Override
	public List<AuthzFeatureOptModel> getFeatureOpts() {
		return getBaseMapper().getFeatureOpts();
	}
	
	@Override
	public List<AuthzFeatureOptModel> getFeatureOptList(String featureId, boolean visible) {
		return getBaseMapper().getFeatureOptList(featureId, visible ? "1" : "0");
	}

	@Override
	public int getOptCountByName(String name, String featureId, String optId) {
		return getBaseMapper().getOptCountByName(name, featureId, optId);
	}

}
