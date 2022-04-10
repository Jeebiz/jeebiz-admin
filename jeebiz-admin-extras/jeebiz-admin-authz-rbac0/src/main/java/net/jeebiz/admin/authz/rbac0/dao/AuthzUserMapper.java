/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserEntity;
import net.jeebiz.boot.api.dao.BaseMapper;
/**
 * 用户管理DAO
 */
@Mapper
public interface AuthzUserMapper extends BaseMapper<AuthzUserEntity>{

	/**
	 * 查询系统所有用户
	 * @return
	 */
	List<AuthzUserEntity> getUserList();

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户id
	 * @return
	 */
	List<String> getRoleKeys(@Param(value="userId") String userId);

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户id
	 * @return
	 */
	List<AuthzRoleEntity> getRoles(@Param(value="userId") String userId);

	/**
	 * 根据[ROLE_PERMISSION_RElatION]数据查询角色具备的权限信息
	 * @param userId 用户id
	 * @return 角色具备的权限信息
	 */
	List<String> getPermissions(@Param(value="userId")String userId);

	/**
	 * 分页查询用户已分配角色信息
	 * @param page
	 * @param model
	 * @return
	 */
	List<AuthzRoleEntity> getPagedAllocatedList(Page<AuthzRoleEntity> page, AuthzUserEntity model);

	/**
	 * 分页查询用户未分配角色信息
	 * @param page
	 * @param model
	 * @return
	 */
	List<AuthzRoleEntity> getPagedUnAllocatedList(Page<AuthzRoleEntity> page, AuthzUserEntity model);

}
