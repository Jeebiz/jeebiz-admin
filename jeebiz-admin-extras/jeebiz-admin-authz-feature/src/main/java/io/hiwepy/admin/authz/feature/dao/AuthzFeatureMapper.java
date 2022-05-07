/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface AuthzFeatureMapper extends BaseMapper<AuthzFeatureEntity> {

	/**
	 * 查询所有的功能菜单
	 * @return
	 */
	List<AuthzFeatureEntity> getFeatureList();

	/**
	 * 查询指定父级菜单下所有可用的菜单
	 * @param id
	 * @return
	 */
	List<AuthzFeatureEntity> getChildFeatureList(@Param("id") String id);

	AuthzFeatureEntity getFeature(@Param("id") String id);

}
