/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.hiwepy.admin.authz.rbac0.dao.entities.AccountStatusModel;
import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.boot.api.dao.BaseMapper;
/**
 * 用户管理DAO
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountEntity>{

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @param password : 密码，可不填
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatus(@Param(value = "account") String account,
										@Param(value = "password") String password);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatusWithoutPwd(@Param(value = "account") String account);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param userId : 用户ID
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatusByUserId(@Param(value = "userId") String userId);

	/**
	 * 根据用户id和密码查询用户可否登录，角色数量等信息
	 * @param id : 用户名
	 * @return 用户账号状态信息
	 */
	AccountStatusModel getAccountStatusById(@Param(value = "id") String id);

	/**
	 * 查询系统所有用户
	 * @return
	 */
	List<UserAccountEntity> getUserList();

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
	List<RoleEntity> getRoles(@Param(value="userId") String userId);

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
	List<RoleEntity> getPagedAllocatedList(Page<RoleEntity> page, UserAccountEntity model);

	/**
	 * 分页查询用户未分配角色信息
	 * @param page
	 * @param model
	 * @return
	 */
	List<RoleEntity> getPagedUnAllocatedList(Page<RoleEntity> page, UserAccountEntity model);

}
