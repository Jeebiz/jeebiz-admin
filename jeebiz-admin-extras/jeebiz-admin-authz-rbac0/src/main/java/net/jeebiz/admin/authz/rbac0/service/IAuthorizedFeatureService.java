/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service;

import java.util.List;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;
import net.jeebiz.boot.api.service.IBaseService;

/**
 * 用户已授权功能菜单Service接口
 */
public interface IAuthorizedFeatureService extends IBaseService<AuthzFeatureModel> {

	/**
	 * 查询指定角色id拥有的功能菜单
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureModel> getFeatures(String roleId);
	
	/**
	 * 查找功能操作并标记指定角色拥有权限的功能操作选中状态
	 * @param roleId
	 * @return
	 */
	public List<AuthzFeatureOptModel> getFeatureOpts(String roleId);
	
	/**
	 * 根据功能菜单id查询子菜单
	 * @param servId
	 * @return
	 */
	public AuthzFeatureDTO getChildFeatures(String roleId, String id);
	
}

