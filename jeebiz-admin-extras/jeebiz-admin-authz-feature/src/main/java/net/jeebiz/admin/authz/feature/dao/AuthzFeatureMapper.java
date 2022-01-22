/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface AuthzFeatureMapper extends BaseMapper<AuthzFeatureModel> {

	/**
	 * 查询所有的功能菜单
	 * @return
	 */
	List<AuthzFeatureModel> getFeatureList();
	
	/**
	 * 查询指定父级菜单下所有可用的菜单
	 * @param id
	 * @return
	 */
	List<AuthzFeatureModel> getChildFeatureList(@Param("id") String id);

	AuthzFeatureModel getFeature(@Param("id") String id);
	
}
