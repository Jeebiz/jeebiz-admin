/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import net.jeebiz.admin.authz.feature.dao.IAuthzFeatureDao;
import net.jeebiz.admin.authz.rbac0.dao.IAuthzRoleDao;
import net.jeebiz.admin.authz.rbac0.dao.IAuthzUserDao;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserAllotRoleModel;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserModel;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.boot.api.utils.CollectionUtils;
import net.jeebiz.boot.api.utils.RandomString;

@Service
public class AuthzUserServiceImpl extends BaseServiceImpl<AuthzUserModel, IAuthzUserDao> implements IAuthzUserService {
	
	protected RandomString randomString = new RandomString(8);

	@Autowired
	private IAuthzFeatureDao authzFeatureDao;
	@Autowired
	private IAuthzRoleDao authzRoleDao;

	// 加密方式
	private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;
	
	@Override
	public List<AuthzUserModel> getUserList() {
		return getDao().getUserList();
	}
	
	@Override
	public int setStatus(String userId, String status) {
		return getDao().setStatus(userId, status);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(AuthzUserModel model) {
		
		// 盐值，用于和密码混合起来用
        String salt = randomString.nextString();
        // 通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, model.getPassword(), salt, hashIterations);
        
        model.setSalt(salt);
        model.setPassword(hash.toBase64());
        // UID检查重复
 		String uid = randomString.nextNumberString();
 		while (getDao().getCountByUid(uid) != 0) {
 			uid = randomString.nextNumberString();
 		}
        model.setUid(uid);
		int ct = getDao().insert(model);
		getAuthzRoleDao().setUsers(model.getRoleId(), Lists.newArrayList(model.getId()));
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(List<?> list) {
		if(CollectionUtils.isEmpty(list)) {
			return 0;
		}
		getDao().batchDeleteRole(list);
		return getDao().batchDelete(list);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		getDao().deleteRole(id);
		return getDao().delete(id);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(AuthzUserModel model) {
		int ct = getDao().update(model);
		if(StringUtils.isNotBlank(model.getRoleId())) {
			getDao().updateRole(model);
		}
		return ct;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int resetPwd(String userId, String oldPassword, String password) {
		// 查询用户信息
		AuthzUserModel model = getDao().getModel(userId);
        // 通过SimpleHash 来进行加密操作
        SimpleHash oldHash = new SimpleHash(algorithmName, oldPassword, model.getSalt(), hashIterations);
        if (!StringUtils.equals(oldHash.toBase64(), model.getPassword())) {
        	return 0;
        }
        SimpleHash newHash = new SimpleHash(algorithmName, password, model.getSalt(), hashIterations);
		return getDao().updatePwd(userId, newHash.toBase64());
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doAllot(AuthzUserAllotRoleModel model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(AuthzUserAllotRoleModel model) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<AuthzRoleModel> getRoles(String userId) {
		return getDao().getRoles(userId);
	}
	
	@Override
	public List<String> getPermissions(String userId) {
		return getDao().getPermissions(userId);
	}
	
	@Override
	public Page<AuthzRoleModel> getPagedAllocatedList(AuthzUserModel model) {
		
		Page<AuthzRoleModel> page = new Page<AuthzRoleModel>(model.getPageNo(), model.getLimit());
		if(!CollectionUtils.isEmpty(model.getOrders())) {
			for (OrderItem orderBy : model.getOrders()) {
				page.addOrder(orderBy);
			}
		}
		
		List<AuthzRoleModel> records = getDao().getPagedAllocatedList(page, model);
		page.setRecords(records);
		
		return page;
		
	}

	@Override
	public Page<AuthzRoleModel> getPagedUnAllocatedList(AuthzUserModel model) {
		
		Page<AuthzRoleModel> page = new Page<AuthzRoleModel>(model.getPageNo(), model.getLimit());
		if(!CollectionUtils.isEmpty(model.getOrders())) {
			for (OrderItem orderBy : model.getOrders()) {
				page.addOrder(orderBy);
			}
		}
		
		List<AuthzRoleModel> records = getDao().getPagedUnAllocatedList(page, model);
		page.setRecords(records);
		
		return page;
	}

	public IAuthzFeatureDao getAuthzFeatureDao() {
		return authzFeatureDao;
	}
	
	public IAuthzRoleDao getAuthzRoleDao() {
		return authzRoleDao;
	}
	
}
