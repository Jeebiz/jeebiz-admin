/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 服务功能菜单
 */
@Mapper
public interface AuthorizedFeatureMapper extends BaseMapper<AuthzFeatureEntity>{

	/**
	 * 查询指定角色id拥有的功能菜单
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureEntity> getFeatures(@Param(value = "roleId") String roleId);

	/**
	 * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureOptEntity> getFeatureOpts(@Param(value = "roleId") String roleId);

	/**
	 * 查询用户指定功能菜单下已经授权的功能菜单
	 * @return
	 */
	public List<AuthzFeatureEntity> getChildFeatures(@Param(value = "roleId") String roleId, @Param("servId") String servId);

	/**
	 * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureOptEntity> getChildFeatureOpts(@Param(value = "roleId") String roleId);

}
