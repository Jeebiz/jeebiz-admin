/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;

import net.jeebiz.admin.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureModel;
import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptModel;
import net.jeebiz.admin.authz.feature.utils.FeatureNavUtils;
import net.jeebiz.admin.authz.feature.web.dto.AuthzFeatureDTO;
import net.jeebiz.admin.authz.rbac0.dao.IAuthorizedFeatureDao;
import net.jeebiz.admin.authz.rbac0.service.IAuthorizedFeatureService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthorizedFeatureServiceImpl extends BaseServiceImpl<AuthzFeatureModel, IAuthorizedFeatureDao>
		implements IAuthorizedFeatureService {

	@Autowired
	private IAuthzFeatureDao authzFeatureDao;
	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Override
	public List<AuthzFeatureModel> getFeatures(String roleId) {
		// 数据库类型
		String dbType = JdbcUtils.getDbType(dataSourceProperties.getUrl(), null);
		// 角色拥有的功能菜单
		List<AuthzFeatureModel> ownFeatures = getDao().getFeatures(roleId);
		// MySQL数据源，则手动构建树形结构数据
		if (JdbcConstants.MYSQL.equalsIgnoreCase(dbType)) {
			// 所有的功能菜单
			List<AuthzFeatureModel> features = getAuthzFeatureDao().getFeatureList();
			// 为用户拥有的功能菜单指定父级菜单
			return FeatureNavUtils.getFeatureMergedList(features, ownFeatures);
		}
		return ownFeatures;
	}
	
	@Override
	public List<AuthzFeatureOptModel> getFeatureOpts(String roleId) {
		return getDao().getFeatureOpts(roleId);
	}

	@Override
	public AuthzFeatureDTO getChildFeatures(String roleId, String servId) {
		// 服务对应的菜单
		List<AuthzFeatureModel> featureList = getDao().getChildFeatures(roleId, servId);
		// 当前登录角色操作权限
		List<AuthzFeatureOptModel> featureOptList = getDao().getChildFeatureOpts(roleId);
		// 树形结构处理后的数据
		return FeatureNavUtils.getFeatureTree(servId, featureList, featureOptList);
	}

	public IAuthzFeatureDao getAuthzFeatureDao() {
		return authzFeatureDao;
	}

}
