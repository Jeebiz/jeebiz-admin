/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service;

import java.util.List;

import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureEntity;
import io.hiwepy.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import io.hiwepy.admin.authz.feature.web.dto.AuthzFeatureTreeNode;
import io.hiwepy.boot.api.service.IBaseService;

/**
 * 用户已授权功能菜单Service接口
 */
public interface IAuthorizedFeatureService extends IBaseService<AuthzFeatureEntity> {

	/**
	 * 查询指定角色id拥有的功能菜单
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureEntity> getFeatures(String roleId);

	/**
	 * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureOptEntity> getFeatureOpts(String roleId);

	/**
	 * 根据功能菜单id查询子菜单
	 * @param roleId
	 * @param id
	 * @return
	 */
	public AuthzFeatureTreeNode getChildFeatures(String roleId, String id);

}

