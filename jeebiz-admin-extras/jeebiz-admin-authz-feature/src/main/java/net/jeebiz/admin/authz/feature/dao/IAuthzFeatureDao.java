/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzFeatureDao extends BaseDao<AuthzFeatureModel> {

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
	public List<AuthzFeatureModel> getChildFeatureList(@Param("id") String id);
	
}
