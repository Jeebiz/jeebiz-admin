/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserAllotRoleEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserEntity;
import net.jeebiz.boot.api.service.IBaseService;


/**
 * 用户管理Service接口
 */
public interface IAuthzUserService extends IBaseService<AuthzUserEntity> {

	/**
	 * 查询系统所有用户
	 * @return
	 */
	List<AuthzUserEntity> getUserList();

	/**
	 * 当前用户设置密码
	 * @param userId 用户id
	 * @param oldPassword 旧密码
	 * @param password    新密码
	 * @return
	 */
	int resetPwd(String userId,String oldPassword,String password);

	/**
	 * 更新用户状态
	 * @param roleId 用户id
	 * @param status 用户状态（0:禁用|1:可用|2:锁定）
	 * @return
	 */
	int setStatus( String userId, String status);

	/**
	 * 执行用户分配角色逻辑操作
	 * @author 		： hiwepy（001）
	 * @param model
	 * @return
	 */
	int doAllot(AuthzUserAllotRoleEntity model);

	/**
	 * 取消已分配给指定用户的角色
	 * @author 		： hiwepy（001）
	 * @param model
	 * @return
	 */
	int doUnAllot(AuthzUserAllotRoleEntity model);

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户id
	 * @return
	 */
	List<AuthzRoleEntity> getRoles(String userId);

	/**
	 * 查询角色具备的权限标记
	 * @param userId 用户id
	 * @return 用户所属角色具备的权限标记
	 */
	List<String> getPermissions(String userId);

	/**
	 * 分页查询用户已分配角色信息
	 * @param model
	 * @return
	 */
	Page<AuthzRoleEntity> getPagedAllocatedList(AuthzUserEntity model);

	/**
	 * 分页查询用户未分配角色信息
	 * @param model
	 * @return
	 */
	Page<AuthzRoleEntity> getPagedUnAllocatedList(AuthzUserEntity model);

	int delete(String id);

	int update(AuthzUserEntity model);


}
