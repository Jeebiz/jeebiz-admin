/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service.impl;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import net.jeebiz.admin.authz.feature.dao.AuthzFeatureMapper;
import net.jeebiz.admin.authz.rbac0.dao.AuthzRoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.AuthzRolePermsMapper;
import net.jeebiz.admin.authz.rbac0.dao.AuthzUserMapper;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleAllotUserModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.utils.AuthzPermsUtils;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleAllotUserPaginationDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzRoleServiceImpl extends BaseServiceImpl<AuthzRoleMapper, AuthzRoleModel>
		implements IAuthzRoleService {
	
	@Autowired
	private AuthzRolePermsMapper authzRolePermsMapper;
	@Autowired
	private AuthzFeatureMapper authzFeatureMapper;
	@Autowired
	private AuthzUserMapper authzUserMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(AuthzRoleModel model) {
		int ct = getBaseMapper().insert(model);
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 有授权
		if( !CollectionUtils.isEmpty(perms)) {
			// 执行授权
			getAuthzRolePermsMapper().setPerms(model.getId(), perms);
		}
		return ct > 0;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(AuthzRoleModel model) {
		int ct = getBaseMapper().updateById(model);
		// 查询已经授权标记
		List<String> oldperms = getAuthzRolePermsMapper().getPermissions(model.getId());
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 之前没有权限
		if(CollectionUtils.isEmpty(oldperms)) {
			// 执行授权
			getAuthzRolePermsMapper().setPerms(model.getId(), perms);
		}
		// 之前有权限,这里需要筛选出新增的权限和取消的权限
		else {
			// 授权标记增量
			List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
			if(!CollectionUtils.isEmpty(increments)) {
				getAuthzRolePermsMapper().setPerms(model.getId(), increments);
			}
			// 授权标记减量
			List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
			if(!CollectionUtils.isEmpty(decrements)) {
				getAuthzRolePermsMapper().delPerms(model.getId(), decrements);
			}
		}
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updatePermis(AuthzRoleModel model) {
		int ct = getBaseMapper().updateById(model);
		// 查询已经授权标记
		List<String> oldperms = getAuthzRolePermsMapper().getPermissions(model.getId());
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 之前没有权限
		if(CollectionUtils.isEmpty(oldperms)) {
			// 执行授权
			getAuthzRolePermsMapper().setPerms(model.getId(), perms);
		}
		// 之前有权限,这里需要筛选出新增的权限和取消的权限
		else {
			// 授权标记增量
			List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
			if(!CollectionUtils.isEmpty(increments)) {
				getAuthzRolePermsMapper().setPerms(model.getId(), increments);
			}
			// 授权标记减量
			List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
			if(!CollectionUtils.isEmpty(decrements)) {
				getAuthzRolePermsMapper().delPerms(model.getId(), decrements);
			}
		}
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		AuthzRoleModel model = getBaseMapper().selectById(id);
		if (model.getUsers() > 0){
			return -1;
		}
		int ct = getBaseMapper().deleteById(id);
		// 删除授权
		getAuthzRolePermsMapper().delPerms(id, Lists.newArrayList());
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String roleId, String status) {
		return getBaseMapper().setStatus(roleId, status);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doAllot(AuthzRoleAllotUserModel model) {
		int rt = 0;
		for (String userId : model.getUserIds()) {
			// 查询角色与用户是否已经有关联
			List<String> oldRoles = getAuthzUserMapper().getRoleKeys(userId);
			if (CollectionUtils.isEmpty(oldRoles) || !oldRoles.contains(model.getRoleId())){
				rt += getBaseMapper().setUsers(model.getRoleId(), Arrays.asList(userId));
			}
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(AuthzRoleAllotUserModel model) {
		return getBaseMapper().deleteUsers(model.getRoleId(), model.getUserIds());
	}
	
	@Override
	public List<AuthzRoleModel> getRoles(){
		return getBaseMapper().getRoles();
	}
	
	@Override
	public Page<AuthzUserModel> getPagedAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO) {
		
		Page<AuthzUserModel> page = new Page<AuthzUserModel>(paginationDTO.getPageNo(), paginationDTO.getLimit());
		if(!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
			for (OrderItem orderBy : paginationDTO.getOrders()) {
				page.addOrder(orderBy);
			}
		}
		
		List<AuthzUserModel> records = getBaseMapper().getPagedAllocatedList(page, paginationDTO);
		page.setRecords(records);
		
		return page;
		
	}
	
	@Override
	public Page<AuthzUserModel> getPagedUnAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO) {
		
		Page<AuthzUserModel> page = new Page<AuthzUserModel>(paginationDTO.getPageNo(), paginationDTO.getLimit());
		if(!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
			for (OrderItem orderBy : paginationDTO.getOrders()) {
				page.addOrder(orderBy);
			}
		}
		
		List<AuthzUserModel> records = getBaseMapper().getPagedUnAllocatedList(page, paginationDTO);
		page.setRecords(records);
		
		return page;
		
	}
	
	public AuthzRolePermsMapper getAuthzRolePermsMapper() {
		return authzRolePermsMapper;
	}

	public void setAuthzRolePermsMapper(AuthzRolePermsMapper authzRolePermsMapper) {
		this.authzRolePermsMapper = authzRolePermsMapper;
	}

	public AuthzFeatureMapper getAuthzFeatureMapper() {
		return authzFeatureMapper;
	}

	public void setAuthzFeatureMapper(AuthzFeatureMapper authzFeatureMapper) {
		this.authzFeatureMapper = authzFeatureMapper;
	}

	public AuthzUserMapper getAuthzUserMapper() {
		return authzUserMapper;
	}

	public void setAuthzUserMapper(AuthzUserMapper authzUserMapper) {
		this.authzUserMapper = authzUserMapper;
	}

}
