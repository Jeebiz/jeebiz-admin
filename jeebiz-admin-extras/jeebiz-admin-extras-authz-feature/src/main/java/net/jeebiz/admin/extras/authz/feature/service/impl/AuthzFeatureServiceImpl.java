/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.jeebiz.admin.extras.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.extras.authz.feature.dao.IAuthzFeatureOptDao;
import net.jeebiz.admin.extras.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.extras.authz.feature.service.IAuthzFeatureService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzFeatureServiceImpl extends BaseServiceImpl<AuthzFeatureModel, IAuthzFeatureDao> implements IAuthzFeatureService {
 
	@Autowired
	private IAuthzFeatureOptDao featureOptDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		int rt = super.delete(id);
		getFeatureOptDao().deleteByParent(id);
		return rt;
	}
	
	@Override
	public List<AuthzFeatureModel> getFeatureList() {
		return getDao().getFeatureList();
	}

	public IAuthzFeatureOptDao getFeatureOptDao() {
		return featureOptDao;
	}

	public void setFeatureOptDao(IAuthzFeatureOptDao featureOptDao) {
		this.featureOptDao = featureOptDao;
	}

}
