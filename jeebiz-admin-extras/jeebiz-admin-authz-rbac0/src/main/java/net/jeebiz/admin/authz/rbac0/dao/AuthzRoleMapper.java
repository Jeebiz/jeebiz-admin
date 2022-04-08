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
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleAllotUserPaginationDTO;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 角色管理Mapper
 * @author hiwepy
 */
@Mapper
public interface AuthzRoleMapper extends BaseMapper<AuthzRoleEntity>{

    /**
     * 给角色分配用户
     * @param roleId 角色id
	 * @param userIds 用户id集合
	 * @return 变更记录数
     */
	public int setUsers(@Param(value = "roleId") String roleId , @Param(value = "userIds") List<String> userIds);

	public int setUsersByKey(@Param(value = "roleKey") String roleKey , @Param(value = "userIds") List<String> userIds);

	/**
	 *
	 * 删除角色已分配的用户
	 * @param roleId 角色id
	 * @param userIds 用户id集合
	 * @return 变更记录数
	 */
	public  int deleteUsers(@Param(value = "roleId") String roleId , @Param(value = "userIds") List<String> userIds);

	/**
	 * 更新角色状态
	 * @param roleId 角色id
	 * @param status 角色状态（0:禁用|1:可用）
	 * @return
	 */
	public int setStatus(@Param("roleId") String roleId, @Param("status") String status);

	/**
	 * 重置用户的默认角色
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public int resetPrty(@Param("roleId") String roleId, @Param("userId") String userId);

	/**
	 * 查询系统可用角色信息
	 * @return
	 */
	public List<AuthzRoleEntity> getRoles();

	/**
	 * 查询用户已分配角色信息
	 * @param userId 用户id
	 * @return
	 */
	public List<AuthzRoleEntity> getUserRoles(@Param(value="userId") String userId);

	/**
	 * 分页查询角色已分配用户信息
	 * @param page
	 * @param model
	 * @return
	 */
	public List<AuthzUserEntity> getPagedAllocatedList(Page<AuthzUserEntity> page, AuthzRoleAllotUserPaginationDTO model);

	/**
	 * 分页查询角色未分配用户信息
	 * @param page
	 * @param model
	 * @return
	 */
	public List<AuthzUserEntity> getPagedUnAllocatedList(Page<AuthzUserEntity> page, AuthzRoleAllotUserPaginationDTO model);

}
