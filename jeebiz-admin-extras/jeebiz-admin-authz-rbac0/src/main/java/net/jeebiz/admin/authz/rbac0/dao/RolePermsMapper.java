/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.rbac0.dao.entities.RolePermsEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 角色权限管理Mapper
 * @author hiwepy
 */
@Mapper
public interface RolePermsMapper extends BaseMapper<RolePermsEntity>{

	/**
	 * 给角色分配功能权限
	 * @param roleId 角色id
	 * @param perms 权限标记集合
	 * @return 变更记录数
	 */
	int setPerms(@Param(value = "roleId") String roleId,@Param(value = "perms") List<String> perms);

	/**
	 * 删除角色功能权限
	 * @param roleId 角色id
	 * @param perms 权限标记集合
	 * @return 变更记录数
	 */
	int delPerms(@Param(value = "roleId") String roleId,@Param(value = "perms") List<String> perms);

	/**
	 * 根据[ROLE_PERMISSION_RElatION]数据查询角色具备的权限信息
	 * @param roleId 角色id
	 * @return 角色具备的权限信息
	 */
	List<String> getPermissions(@Param(value="roleId") String roleId);

}
