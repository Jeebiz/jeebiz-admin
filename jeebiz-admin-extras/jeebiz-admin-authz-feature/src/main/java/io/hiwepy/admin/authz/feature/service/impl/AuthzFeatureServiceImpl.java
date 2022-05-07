/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hiwepy.admin.authz.feature.dao.AuthzFeatureMapper;
import io.hiwepy.admin.authz.feature.dao.AuthzFeatureOptMapper;
import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import io.hiwepy.admin.authz.feature.service.IAuthzFeatureService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class AuthzFeatureServiceImpl extends BaseServiceImpl<AuthzFeatureMapper, AuthzFeatureEntity> implements IAuthzFeatureService {

	@Autowired
	private AuthzFeatureOptMapper featureOptMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		boolean rt = super.removeById(id);
		getFeatureOptMapper().deleteByParent(id);
		return rt;
	}

	@Override
	public AuthzFeatureEntity getFeature(String id) {
		return getBaseMapper().getFeature(id);
	}

	@Override
	public List<AuthzFeatureEntity> getFeatureList() {
		return getBaseMapper().getFeatureList();
	}

	@Override
	public List<AuthzFeatureEntity> getChildFeatureList(String id) {
		return getBaseMapper().getChildFeatureList(id);
	}

	public AuthzFeatureOptMapper getFeatureOptMapper() {
		return featureOptMapper;
	}

	public void setFeatureOptMapper(AuthzFeatureOptMapper featureOptMapper) {
		this.featureOptMapper = featureOptMapper;
	}

}
