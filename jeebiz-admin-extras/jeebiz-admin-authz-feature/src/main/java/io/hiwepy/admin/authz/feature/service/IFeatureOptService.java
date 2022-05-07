/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.service;

import java.util.List;

import io.hiwepy.admin.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IFeatureOptService extends IBaseService<FeatureOptEntity>{

	public List<FeatureOptEntity> getFeatureOpts();

	public List<FeatureOptEntity> getFeatureOptList(String featureId, boolean visible);

	public int getOptCountByName(String name, String featureId, String optId);

	public FeatureOptEntity getFeatureOpt(String id);

}
