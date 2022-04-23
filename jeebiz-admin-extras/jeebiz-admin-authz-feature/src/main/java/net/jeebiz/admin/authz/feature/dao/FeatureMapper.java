/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.feature.dao.entities.FeatureEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface FeatureMapper extends BaseMapper<FeatureEntity> {

	/**
	 * 查询所有的功能菜单
	 * @return
	 */
	List<FeatureEntity> getFeatureList();

	/**
	 * 查询指定父级菜单下所有可用的菜单
	 * @param id
	 * @return
	 */
	List<FeatureEntity> getChildFeatureList(@Param("id") String id);

	FeatureEntity getFeature(@Param("id") String id);

}
