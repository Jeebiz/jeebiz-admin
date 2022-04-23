/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.service;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.FeatureOptEntity;
import net.jeebiz.boot.api.service.IBaseService;

public interface IFeatureOptService extends IBaseService<FeatureOptEntity>{

	public List<FeatureOptEntity> getFeatureOpts();

	public List<FeatureOptEntity> getFeatureOptList(String featureId, boolean visible);

	public int getOptCountByName(String name, String featureId, String optId);

	public FeatureOptEntity getFeatureOpt(String id);

}
