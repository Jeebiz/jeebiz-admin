/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.hiwepy.admin.authz.rbac0.dao.entities.AccountStatusModel;
import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserRoleEntity;
import io.hiwepy.boot.api.service.IBaseService;


/**
 * 用户管理Service接口
 */
public interface IUserAccountService extends IBaseService<UserAccountEntity> {

	/**
	 * 查询系统所有用户
	 * @return
	 */
	List<UserAccountEntity> getUserList();

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
	 * @param userId 用户id
	 * @param status 用户状态（0:禁用|1:可用|2:锁定）
	 * @return
	 */
	@Override
	int setStatus( String userId, String status);

	/**
	 * 执行用户分配角色逻辑操作
	 * @author 		： hiwepy（001）
	 * @param model
	 * @return
	 */
	int doAllot(UserRoleEntity model);

	/**
	 * 取消已分配给指定用户的角色
	 * @author 		： hiwepy（001）
	 * @param model
	 * @return
	 */
	int doUnAllot(UserRoleEntity model);

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户ID
	 * @return
	 */
	List<String> getRoleKeys(String userId);

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户id
	 * @return
	 */
	List<RoleEntity> getRoles(String userId);

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
	Page<RoleEntity> getPagedAllocatedList(UserAccountEntity model);

	/**
	 * 分页查询用户未分配角色信息
	 * @param model
	 * @return
	 */
	Page<RoleEntity> getPagedUnAllocatedList(UserAccountEntity model);

	int delete(String id);

	int update(UserAccountEntity model);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @param password : 密码，可不填
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatus(String account, String password);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatusWithoutPwd(String account);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param userId : 用户ID
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatusByUserId(String userId);

	/***
	 * 根据用户id无密码查询用户信息；用于单点登录
	 * @param type : 登录方式
	 * @param account : 用户名
	 * @return 用户登录信息
	 */
	UserAccountEntity getAccountByType(String type, String account);

	/**
	 * 根据用户表id查询当前系统对应的用户信息
	 * @param id 账号表ID
	 * @return
	 */
	UserAccountEntity getAccountById(String id);

	/**
	 * 根据用户业务id查询当前系统对应的用户信息
	 * @param userCode 用户业务id
	 * @return
	 */
	UserAccountEntity getAccountByUcode(String userCode);

	/**
	 * 根据用户id查询当前系统对应的用户信息
	 * @param userId 用户ID
	 * @return
	 */
	UserAccountEntity getAccountByUserId(String userId);

}
