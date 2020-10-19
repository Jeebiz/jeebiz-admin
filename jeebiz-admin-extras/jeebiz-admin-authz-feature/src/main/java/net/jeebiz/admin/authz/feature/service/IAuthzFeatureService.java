/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.service;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthzFeatureService extends IBaseService<AuthzFeatureModel>{

	/**
	 * 查询所有的功能菜单
	 * @return
	 */
	public List<AuthzFeatureModel> getFeatureList();
	
	/**
	 * 查询指定父级菜单下所有可用的菜单
	 * @param id
	 * @return
	 */
	public List<AuthzFeatureModel> getChildFeatureList(String id);
	
}
