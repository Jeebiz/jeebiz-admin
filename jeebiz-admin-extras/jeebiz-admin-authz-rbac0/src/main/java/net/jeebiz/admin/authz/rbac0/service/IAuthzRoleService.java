/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleAllotUserModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleAllotUserPaginationDTO;
import net.jeebiz.boot.api.service.IBaseService;

/**
 * @author hiwepy
 */
public interface IAuthzRoleService extends IBaseService<AuthzRoleModel>{

	/**
	 * 执行分配用户逻辑操作
	 * @param model
	 * @return
	 */
	int doAllot(AuthzRoleAllotUserModel model);

	/**
	 * 取消已分配给指定角色的用户
	 * @param model
	 * @return
	 */
	int doUnAllot(AuthzRoleAllotUserModel model);

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
	List<AuthzRoleModel> getRoles();

	/**
	 * 分页查询角色已分配用户信息
	 * @param paginationDTO
	 * @return
	 */
	Page<AuthzUserModel> getPagedAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO);

	/**
	 * 分页查询角色未分配用户信息
	 * @param paginationDTO
	 * @return
	 */
	Page<AuthzUserModel> getPagedUnAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO);

	int update(AuthzRoleModel model);

	int updatePermis(AuthzRoleModel model);

	int delete(String id);
	
}