/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service.impl;


import java.util.Arrays;
import java.util.List;

import net.jeebiz.admin.authz.rbac0.dao.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import net.jeebiz.admin.authz.feature.dao.FeatureMapper;
import net.jeebiz.admin.authz.rbac0.dao.RoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.RolePermsMapper;
import net.jeebiz.admin.authz.rbac0.dao.UserAccountMapper;
import net.jeebiz.admin.authz.rbac0.dao.UserRoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.entities.UserAccountEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.UserRoleEntity;
import net.jeebiz.admin.authz.rbac0.service.IRoleService;
import net.jeebiz.admin.authz.rbac0.utils.AuthzPermsUtils;
import net.jeebiz.admin.authz.rbac0.web.dto.RoleAllotUserDTO;
import net.jeebiz.admin.authz.rbac0.web.dto.RoleAllotUserPaginationDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, RoleEntity>
		implements IRoleService {

	@Autowired
	private RolePermsMapper rolePermsMapper;
	@Autowired
	private FeatureMapper featureMapper;
	@Autowired
	private UserAccountMapper userAccountMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(RoleEntity model) {
		int ct = getBaseMapper().insert(model);
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 有授权
		if( !CollectionUtils.isEmpty(perms)) {
			// 执行授权
			getRolePermsMapper().setPerms(model.getId(), perms);
		}
		return ct > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(RoleEntity model) {
		int ct = getBaseMapper().updateById(model);
		// 查询已经授权标记
		List<String> oldperms = getRolePermsMapper().getPermissions(model.getId());
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 之前没有权限
		if(CollectionUtils.isEmpty(oldperms)) {
			// 执行授权
			getRolePermsMapper().setPerms(model.getId(), perms);
		}
		// 之前有权限,这里需要筛选出新增的权限和取消的权限
		else {
			// 授权标记增量
			List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
			if(!CollectionUtils.isEmpty(increments)) {
				getRolePermsMapper().setPerms(model.getId(), increments);
			}
			// 授权标记减量
			List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
			if(!CollectionUtils.isEmpty(decrements)) {
				getRolePermsMapper().delPerms(model.getId(), decrements);
			}
		}
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updatePermis(RoleEntity model) {
		int ct = getBaseMapper().updateById(model);
		// 查询已经授权标记
		List<String> oldperms = getRolePermsMapper().getPermissions(model.getId());
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 之前没有权限
		if(CollectionUtils.isEmpty(oldperms)) {
			// 执行授权
			getRolePermsMapper().setPerms(model.getId(), perms);
		}
		// 之前有权限,这里需要筛选出新增的权限和取消的权限
		else {
			// 授权标记增量
			List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
			if(!CollectionUtils.isEmpty(increments)) {
				getRolePermsMapper().setPerms(model.getId(), increments);
			}
			// 授权标记减量
			List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
			if(!CollectionUtils.isEmpty(decrements)) {
				getRolePermsMapper().delPerms(model.getId(), decrements);
			}
		}
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		RoleEntity model = getBaseMapper().selectById(id);
		if (model.getUsers() > 0){
			return -1;
		}
		int ct = getBaseMapper().deleteById(id);
		// 删除授权
		getRolePermsMapper().delPerms(id, Lists.newArrayList());
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String roleId, String status) {
		return getBaseMapper().setStatus(roleId, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doAllot(RoleAllotUserDTO model) {
		int rt = 0;
		for (String userId : model.getUserIds()) {
			// 查询角色与用户是否已经有关联
			List<String> oldRoles = getUserMapper().getRoleKeys(userId);
			if (CollectionUtils.isEmpty(oldRoles) || !oldRoles.contains(model.getRoleId())){
				rt += getBaseMapper().setUsers(model.getRoleId(), Arrays.asList(userId));
			}
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(RoleAllotUserDTO model) {
		return getUserRoleMapper().delete(new QueryWrapper<UserRoleEntity>()
				.eq("role_id", model.getRoleId())
				.in("user_id", model.getUserIds()));
	}

	@Override
	public List<RoleEntity> getRoles(){
		return getBaseMapper().getRoles();
	}

	@Override
	public Page<UserAccountEntity> getPagedAllocatedList(RoleAllotUserPaginationDTO paginationDTO) {

		Page<UserAccountEntity> page = new Page<UserAccountEntity>(paginationDTO.getPageNo(), paginationDTO.getLimit());
		if(!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
			for (OrderItem orderBy : paginationDTO.getOrders()) {
				page.addOrder(orderBy);
			}
		}

		List<UserAccountEntity> records = getBaseMapper().getPagedAllocatedList(page, paginationDTO);
		page.setRecords(records);

		return page;

	}

	@Override
	public Page<UserAccountEntity> getPagedUnAllocatedList(RoleAllotUserPaginationDTO paginationDTO) {

		Page<UserAccountEntity> page = new Page<UserAccountEntity>(paginationDTO.getPageNo(), paginationDTO.getLimit());
		if(!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
			for (OrderItem orderBy : paginationDTO.getOrders()) {
				page.addOrder(orderBy);
			}
		}

		List<UserAccountEntity> records = getBaseMapper().getPagedUnAllocatedList(page, paginationDTO);
		page.setRecords(records);

		return page;

	}

	public RolePermsMapper getRolePermsMapper() {
		return rolePermsMapper;
	}

	public FeatureMapper getFeatureMapper() {
		return featureMapper;
	}

	public UserAccountMapper getUserMapper() {
		return userAccountMapper;
	}

	public UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}
}
