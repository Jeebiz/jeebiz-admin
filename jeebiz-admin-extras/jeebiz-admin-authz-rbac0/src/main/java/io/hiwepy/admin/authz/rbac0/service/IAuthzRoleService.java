/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.hiwepy.admin.authz.rbac0.dao.entities.AuthzRoleEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.AuthzUserEntity;
import io.hiwepy.admin.authz.rbac0.web.dto.AuthzRoleAllotUserDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.AuthzRoleAllotUserPaginationDTO;
import io.hiwepy.boot.api.service.IBaseService;

/**
 * @author hiwepy
 */
public interface IAuthzRoleService extends IBaseService<AuthzRoleEntity>{

	/**
	 * 执行分配用户逻辑操作
	 * @param model
	 * @return
	 */
	int doAllot(AuthzRoleAllotUserDTO model);

	/**
	 * 取消已分配给指定角色的用户
	 * @param model
	 * @return
	 */
	int doUnAllot(AuthzRoleAllotUserDTO model);

	/**
	 * 更新角色状态
	 * @param roleId 角色id
	 * @param status 角色状态（0:禁用|1:可用）
	 * @return
	 */
	int setStatus( String roleId, String status);

	/**
	 * 查询系统可用角色信息
	 * @return
	 */
	List<AuthzRoleEntity> getRoles();

	/**
	 * 分页查询角色已分配用户信息
	 * @param paginationDTO
	 * @return
	 */
	Page<AuthzUserEntity> getPagedAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO);

	/**
	 * 分页查询角色未分配用户信息
	 * @param paginationDTO
	 * @return
	 */
	Page<AuthzUserEntity> getPagedUnAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO);

	int update(AuthzRoleEntity model);

	int updatePermis(AuthzRoleEntity model);

	int delete(String id);

}
