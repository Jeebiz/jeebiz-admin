/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.service;

import java.util.List;

import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IAuthzFeatureOptService extends IBaseService<AuthzFeatureOptEntity>{

	public List<AuthzFeatureOptEntity> getFeatureOpts();

	public List<AuthzFeatureOptEntity> getFeatureOptList(String featureId, boolean visible);

	public int getOptCountByName(String name, String featureId, String optId);

	public AuthzFeatureOptEntity getFeatureOpt(String id);

}
