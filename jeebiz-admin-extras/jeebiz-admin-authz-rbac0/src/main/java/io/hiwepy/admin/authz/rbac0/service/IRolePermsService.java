/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service;


import java.util.List;

import io.hiwepy.admin.authz.rbac0.dao.entities.RolePermsEntity;
import io.hiwepy.boot.api.service.IBaseService;

/**
 *
 * @author <a href="https://github.com/hiwepy">wandl</a>
 */
public interface IRolePermsService extends IBaseService<RolePermsEntity>{

	/**
	 * 查询角色具备的权限标记
	 * @param roleId 角色id
	 * @return 角色具备的权限标记
	 */
	List<String> getPermissions(String roleId);

	/**
	 * 执行角色分配权限逻辑操作
	 * @param roleId 角色id
	 * @param perms 权限标记
	 * @return
	 */
	int doPerms(String roleId, List<String> perms);

	/**
	 * 取消已分配给指定角色的权限
	 * @param roleId 角色id
	 * @param perms 权限标记
	 * @return
	 */
	int unPerms(String roleId, List<String> perms);

}
