/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.service;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import net.jeebiz.boot.api.service.IBaseService;

public interface IAuthzFeatureService extends IBaseService<AuthzFeatureEntity>{

	/**
	 * ��ѯ���еĹ��ܲ˵�
	 * @return
	 */
	public List<AuthzFeatureEntity> getFeatureList();

	/**
	 * ��ѯָ�������˵������п��õĲ˵�
	 * @param id
	 * @return
	 */
	public List<AuthzFeatureEntity> getChildFeatureList(String id);

	public AuthzFeatureEntity getFeature(String id);

}
