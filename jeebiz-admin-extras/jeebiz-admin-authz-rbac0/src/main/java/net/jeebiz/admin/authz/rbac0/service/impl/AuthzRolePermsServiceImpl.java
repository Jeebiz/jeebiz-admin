/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.jeebiz.admin.authz.rbac0.dao.AuthzRolePermsMapper;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRolePermsEntity;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRolePermsService;
import net.jeebiz.admin.authz.rbac0.utils.AuthzPermsUtils;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzRolePermsServiceImpl extends BaseServiceImpl<AuthzRolePermsMapper, AuthzRolePermsEntity>
		implements IAuthzRolePermsService {

	@Override
	public List<String> getPermissions(String roleId) {
		return getBaseMapper().getPermissions(roleId);
	}

	@Override
	public int doPerms(String roleId, List<String> perms) {
		// 此次提交的授权标记
		perms = AuthzPermsUtils.distinct(perms);
		// 有授权
		if( !CollectionUtils.isEmpty(perms)) {
			// 执行授权
			return getBaseMapper().setPerms(roleId, perms);
		}
		return 0;
	}

	@Override
	public int unPerms(String roleId, List<String> perms) {
		// 查询已经授权标记
		List<String> oldperms = getBaseMapper().getPermissions(roleId);
		// 此次提交的授权标记
		perms = AuthzPermsUtils.distinct(perms);
		// 之前没有权限
		if(CollectionUtils.isEmpty(oldperms)) {
			// 执行授权
			return getBaseMapper().setPerms(roleId, perms);
		}
		// 之前有权限,这里需要筛选出新增的权限和取消的权限
		else {
			int count = 0;
			// 授权标记增量
			List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
			if(!CollectionUtils.isEmpty(increments)) {
				count += getBaseMapper().setPerms(roleId, increments);
			}
			// 授权标记减量
			List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
			if(!CollectionUtils.isEmpty(decrements)) {
				getBaseMapper().delPerms(roleId, decrements);
			}
			return count;
		}
	}

}
