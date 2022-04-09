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
	 * 通过用户id查询用户信息
	 * @param id
	 * @return
	 */
	AuthzUserEntity getUserById(String id);

	/**
	 * 查询系统所有用户
	 * @return
	 */
	List<AuthzUserEntity> getUserList();

	/**
	 * 删除用户角色记录
	 * @param id
	 * @return
	 */
	int deleteRole(String id);

	/**
	 * 批量删除用户角色
	 * @param list
	 * @return
	 */
	public int batchDeleteRole(@Param(value="list") List<?> list);

	/**
	 * 更新用户角色记录
	 * @param model
	 * @return
	 */
	public int updateRole(AuthzUserEntity model);

	/**
	 * 批量修改用户密码
	 * @param userid 用户id
	 * @param password 新密码
	 * @return 变更记录数
	 */
	public int updatePwd(@Param(value="userId") String userId , @Param(value = "password") String password);

	/**
	 * 当前用户设置密码
	 * @param userId 用户id
	 * @param oldPassword 旧密码
	 * @param password    新密码
	 * @return
	 */
	public int resetPwd(@Param(value="userId") String userId, @Param(value="oldPassword") String oldPassword,@Param(value="password") String password);

	/**
	 * 更新用户状态
	 * @param roleId 用户id
	 * @param status 用户状态（0:禁用|1:可用|2:锁定）
	 * @return
	 */
	public int setStatus(@Param("userId") String userId, @Param("status") String status);

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户id
	 * @return
	 */
	public List<String> getRoleKeys(@Param(value="userId") String userId);

	/**
	 * 获取用户已分配角色id
	 * @param userId 用户id
	 * @return
	 */
	public List<AuthzRoleEntity> getRoles(@Param(value="userId") String userId);

	/**
	 * 根据[ROLE_PERMISSION_RElatION]数据查询角色具备的权限信息
	 * @param userId 用户id
	 * @return 角色具备的权限信息
	 */
	public List<String> getPermissions(@Param(value="userId")String userId);

	/**
	 * 分页查询用户已分配角色信息
	 * @param page
	 * @param model
	 * @return
	 */
	public List<AuthzRoleEntity> getPagedAllocatedList(Page<AuthzRoleEntity> page, AuthzUserEntity model);

	/**
	 * 分页查询用户未分配角色信息
	 * @param page
	 * @param model
	 * @return
	 */
	public List<AuthzRoleEntity> getPagedUnAllocatedList(Page<AuthzRoleEntity> page, AuthzUserEntity model);


}
