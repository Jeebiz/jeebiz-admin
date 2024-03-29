/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.service;

import java.util.List;

import io.hiwepy.admin.authz.feature.dao.entities.FeatureEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IFeatureService extends IBaseService<FeatureEntity>{

	/**
	 * ��ѯ���еĹ��ܲ˵�
	 * @return
	 */
	public List<FeatureEntity> getFeatureList();

	/**
	 * ��ѯָ�������˵������п��õĲ˵�
	 * @param id
	 * @return
	 */
	public List<FeatureEntity> getChildFeatureList(String id);

	public FeatureEntity getFeature(String id);

}
