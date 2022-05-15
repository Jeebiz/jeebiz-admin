/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleAllotUserPaginationDTO;
import io.hiwepy.boot.api.dao.BaseMapper;

/**
 * 角色管理Mapper
 * @author hiwepy
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity>{

    /**
     * 给角色分配用户
     * @param roleId 角色id
	 * @param userIds 用户id集合
	 * @return 变更记录数
     */
	int setUsers(@Param(value = "roleId") String roleId , @Param(value = "userIds") List<String> userIds);

	int setUsersByKey(@Param(value = "roleKey") String roleKey , @Param(value = "userIds") List<String> userIds);

	/**
	 * 查询系统可用角色信息
	 * @return
	 */
	List<RoleEntity> getRoles();

	/**
	 * 查询用户已分配角色信息
	 * @param userId 用户id
	 * @return
	 */
	List<RoleEntity> getUserRoles(@Param(value="userId") String userId);

	/**
	 * 分页查询角色已分配用户信息
	 * @param page
	 * @param model
	 * @return
	 */
	List<UserAccountEntity> getPagedAllocatedList(Page<UserAccountEntity> page, RoleAllotUserPaginationDTO model);

	/**
	 * 分页查询角色未分配用户信息
	 * @param page
	 * @param model
	 * @return
	 */
	List<UserAccountEntity> getPagedUnAllocatedList(Page<UserAccountEntity> page, RoleAllotUserPaginationDTO model);

}
