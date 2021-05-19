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

import net.jeebiz.admin.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.authz.feature.dao.IAuthzFeatureOptDao;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class AuthzFeatureServiceImpl extends BaseMapperServiceImpl<AuthzFeatureModel, IAuthzFeatureDao> implements IAuthzFeatureService {
 
	@Autowired
	private IAuthzFeatureOptDao featureOptDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		boolean rt = super.removeById(id);
		getFeatureOptDao().deleteByParent(id);
		return rt;
	}

	@Override
	public AuthzFeatureModel getFeature(String id) {
		return getBaseMapper().getFeature(id);
	}

	@Override
	public List<AuthzFeatureModel> getFeatureList() {
		return getBaseMapper().getFeatureList();
	}
	
	@Override
	public List<AuthzFeatureModel> getChildFeatureList(String id) {
		return getBaseMapper().getChildFeatureList(id);
	}

	public IAuthzFeatureOptDao getFeatureOptDao() {
		return featureOptDao;
	}

	public void setFeatureOptDao(IAuthzFeatureOptDao featureOptDao) {
		this.featureOptDao = featureOptDao;
	}

}
