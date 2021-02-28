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

import net.jeebiz.admin.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.authz.rbac0.dao.IAuthzRoleDao;
import net.jeebiz.admin.authz.rbac0.dao.IAuthzRolePermsDao;
import net.jeebiz.admin.authz.rbac0.dao.IAuthzUserDao;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleAllotUserModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzRoleService;
import net.jeebiz.admin.authz.rbac0.utils.AuthzPermsUtils;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzRoleAllotUserPaginationDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzRoleServiceImpl extends BaseServiceImpl<AuthzRoleModel, IAuthzRoleDao>
		implements IAuthzRoleService {
	
	@Autowired
	private IAuthzRolePermsDao authzRolePermsDao;
	@Autowired
	private IAuthzFeatureDao authzFeatureDao;
	@Autowired
	private IAuthzUserDao authzUserDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(AuthzRoleModel model) {
		int ct = getDao().insert(model);
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 有授权
		if( !CollectionUtils.isEmpty(perms)) {
			// 执行授权
			getAuthzRolePermsDao().setPerms(model.getId(), perms);
		}
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(AuthzRoleModel model) {
		int ct = getDao().update(model);
		// 查询已经授权标记
		List<String> oldperms = getAuthzRolePermsDao().getPermissions(model.getId());
		// 此次提交的授权标记
		List<String> perms = AuthzPermsUtils.distinct(model.getPerms());
		// 之前没有权限
		if(CollectionUtils.isEmpty(oldperms)) {
			// 执行授权
			getAuthzRolePermsDao().setPerms(model.getId(), perms);
		}
		// 之前有权限,这里需要筛选出新增的权限和取消的权限
		else {
			// 授权标记增量
			List<String> increments = AuthzPermsUtils.increment(perms, oldperms);
			if(!CollectionUtils.isEmpty(increments)) {
				getAuthzRolePermsDao().setPerms(model.getId(), increments);
			}
			// 授权标记减量
			List<String> decrements = AuthzPermsUtils.decrement(perms, oldperms);
			if(!CollectionUtils.isEmpty(decrements)) {
				getAuthzRolePermsDao().delPerms(model.getId(), decrements);
			}
		}
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		AuthzRoleModel model = getDao().getModel(id);
		if (model.getUsers() > 0){
			return -1;
		}
		int ct = getDao().delete(id);
		// 删除授权
		getAuthzRolePermsDao().delPerms(id, Lists.newArrayList());
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String roleId, String status) {
		return getDao().setStatus(roleId, status);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doAllot(AuthzRoleAllotUserModel model) {
		int rt = 0;
		for (String userId : model.getUserIds()) {
			// 查询角色与用户是否已经有关联
			List<String> oldRoles = getAuthzUserDao().getRoleKeys(userId);
			if (CollectionUtils.isEmpty(oldRoles) || !oldRoles.contains(model.getRoleId())){
				rt += getDao().setUsers(model.getRoleId(), Arrays.asList(userId));
			}
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(AuthzRoleAllotUserModel model) {
		return getDao().deleteUsers(model.getRoleId(), model.getUserIds());
	}
	
	@Override
	public List<AuthzRoleModel> getRoles(){
		return getDao().getRoles();
	}
	
	@Override
	public Page<AuthzUserModel> getPagedAllocatedList(AuthzRoleAllotUserPaginationDTO paginationDTO) {
		
		Page<AuthzUserModel> page = new Page<AuthzUserModel>(paginationDTO.getPageNo(), paginationDTO.getLimit());
		if(!CollectionUtils.isEmpty(paginationDTO.getOrders())) {
			for (OrderItem orderBy : paginationDTO.getOrders()) {
				page.addOrder(orderBy);
			}
		}
		
		List<AuthzUserModel> records = getDao().getPagedAllocatedList(page, paginationDTO);
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
		
		List<AuthzUserModel> records = getDao().getPagedUnAllocatedList(page, paginationDTO);
		page.setRecords(records);
		
		return page;
		
	}
	
	public IAuthzRolePermsDao getAuthzRolePermsDao() {
		return authzRolePermsDao;
	}

	public void setAuthzRolePermsDao(IAuthzRolePermsDao authzRolePermsDao) {
		this.authzRolePermsDao = authzRolePermsDao;
	}

	public IAuthzFeatureDao getAuthzFeatureDao() {
		return authzFeatureDao;
	}

	public void setAuthzFeatureDao(IAuthzFeatureDao authzFeatureDao) {
		this.authzFeatureDao = authzFeatureDao;
	}

	public IAuthzUserDao getAuthzUserDao() {
		return authzUserDao;
	}

	public void setAuthzUserDao(IAuthzUserDao authzUserDao) {
		this.authzUserDao = authzUserDao;
	}

}
