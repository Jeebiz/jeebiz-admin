/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.jeebiz.admin.authz.feature.dao.FeatureMapper;
import net.jeebiz.admin.authz.feature.dao.FeatureOptMapper;
import net.jeebiz.admin.authz.feature.dao.entities.FeatureEntity;
import net.jeebiz.admin.authz.feature.service.IFeatureService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class FeatureServiceImpl extends BaseServiceImpl<FeatureMapper, FeatureEntity> implements IFeatureService {

	@Autowired
	private FeatureOptMapper featureOptMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		boolean rt = super.removeById(id);
		getFeatureOptMapper().deleteByParent(id);
		return rt;
	}

	@Override
	public FeatureEntity getFeature(String id) {
		return getBaseMapper().getFeature(id);
	}

	@Override
	public List<FeatureEntity> getFeatureList() {
		return getBaseMapper().getFeatureList();
	}

	@Override
	public List<FeatureEntity> getChildFeatureList(String id) {
		return getBaseMapper().getChildFeatureList(id);
	}

	public FeatureOptMapper getFeatureOptMapper() {
		return featureOptMapper;
	}

	public void setFeatureOptMapper(FeatureOptMapper featureOptMapper) {
		this.featureOptMapper = featureOptMapper;
	}

}
