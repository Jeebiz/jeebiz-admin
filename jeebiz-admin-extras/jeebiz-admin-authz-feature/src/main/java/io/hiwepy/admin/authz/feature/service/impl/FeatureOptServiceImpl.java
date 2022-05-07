/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.feature.dao.FeatureOptMapper;
import io.hiwepy.admin.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.admin.authz.feature.service.IFeatureOptService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class FeatureOptServiceImpl extends BaseServiceImpl<FeatureOptMapper, FeatureOptEntity> implements IFeatureOptService {

	@Override
	public FeatureOptEntity getFeatureOpt(String id) {
		return getBaseMapper().getFeatureOpt(id);
	}

	@Override
	public List<FeatureOptEntity> getFeatureOpts() {
		return getBaseMapper().getFeatureOpts();
	}

	@Override
	public List<FeatureOptEntity> getFeatureOptList(String featureId, boolean visible) {
		return getBaseMapper().getFeatureOptList(featureId, visible ? "1" : "0");
	}

	@Override
	public int getOptCountByName(String name, String featureId, String optId) {
		return getBaseMapper().getOptCountByName(name, featureId, optId);
	}

}
