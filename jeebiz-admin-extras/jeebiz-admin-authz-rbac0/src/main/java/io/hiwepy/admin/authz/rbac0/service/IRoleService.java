/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleAllotUserDTO;
import io.hiwepy.admin.authz.rbac0.web.dto.RoleAllotUserPaginationDTO;
import io.hiwepy.boot.api.service.IBaseService;

/**
 * @author hiwepy
 */
public interface IRoleService extends IBaseService<RoleEntity>{

	/**
	 * 执行分配用户逻辑操作
	 * @param model
	 * @return
	 */
	int doAllot(RoleAllotUserDTO model);

	/**
	 * 取消已分配给指定角色的用户
	 * @param model
	 * @return
	 */
	int doUnAllot(RoleAllotUserDTO model);

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
	List<RoleEntity> getRoles();

	/**
	 * 分页查询角色已分配用户信息
	 * @param paginationDTO
	 * @return
	 */
	Page<UserAccountEntity> getPagedAllocatedList(RoleAllotUserPaginationDTO paginationDTO);

	/**
	 * 分页查询角色未分配用户信息
	 * @param paginationDTO
	 * @return
	 */
	Page<UserAccountEntity> getPagedUnAllocatedList(RoleAllotUserPaginationDTO paginationDTO);

	int update(RoleEntity model);

	int updatePermis(RoleEntity model);

	int delete(String id);

}
