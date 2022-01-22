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
	 * ��ѯ���еĹ��ܲ˵�
	 * @return
	 */
	public List<AuthzFeatureModel> getFeatureList();
	
	/**
	 * ��ѯָ�������˵������п��õĲ˵�
	 * @param id
	 * @return
	 */
	public List<AuthzFeatureModel> getChildFeatureList(String id);

	public AuthzFeatureModel getFeature(String id);
	
}
