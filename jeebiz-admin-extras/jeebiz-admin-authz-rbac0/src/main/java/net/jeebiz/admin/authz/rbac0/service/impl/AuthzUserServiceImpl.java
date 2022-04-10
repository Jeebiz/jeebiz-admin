/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import hitool.core.collections.CollectionUtils;
import hitool.core.lang3.RandomString;
import net.jeebiz.admin.authz.feature.dao.AuthzFeatureMapper;
import net.jeebiz.admin.authz.rbac0.dao.AuthzRoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.AuthzUserMapper;
import net.jeebiz.admin.authz.rbac0.dao.AuthzUserRoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserEntity;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserRoleEntity;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzUserServiceImpl extends BaseServiceImpl<AuthzUserMapper, AuthzUserEntity> implements IAuthzUserService {

	protected RandomString randomString = new RandomString(8);

	@Autowired
	private AuthzFeatureMapper featureMapper;
	@Autowired
	private AuthzRoleMapper roleMapper;
	@Autowired
	private AuthzUserRoleMapper userRoleMapper;

	// 加密方式
	private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;

	@Override
	public List<AuthzUserEntity> getUserList() {
		return getBaseMapper().getUserList();
	}

	@Override
	public int setStatus(String userId, String status) {
		return getBaseMapper().setStatus(userId, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(AuthzUserEntity model) {

		// 盐值，用于和密码混合起来用
        String salt = randomString.nextString();
        // 通过SimpleHash 来进行加密操作
        SimpleHash hash = new SimpleHash(algorithmName, model.getPassword(), salt, hashIterations);

        model.setSalt(salt);
        model.setPassword(hash.toBase64());
        // Uid检查重复
 		String uid = randomString.nextNumberString();
 		while (getBaseMapper().getCountByUid(uid) != 0) {
 			uid = randomString.nextNumberString();
 		}
        model.setUserCode(uid);
		int ct = getBaseMapper().insert(model);
		getRoleMapper().setUsers(model.getRoleId(), Lists.newArrayList(model.getId()));
		return ct > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<?> idList) {
		if(CollectionUtils.isEmpty(idList)) {
			return false;
		}
		//getBaseMapper().batchDeleteRole(idList);
		return getBaseMapper().deleteBatchIds(idList) > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String id) {
		getUserRoleMapper().delete(new QueryWrapper<AuthzUserRoleEntity>().eq("user_id", id));
		return getBaseMapper().deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(AuthzUserEntity model) {
		int ct = getBaseMapper().updateById(model);
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int resetPwd(String userId, String oldPassword, String password) {
		// 查询用户信息
		AuthzUserEntity entity = getBaseMapper().selectById(userId);
        // 通过SimpleHash 来进行加密操作
        SimpleHash oldHash = new SimpleHash(algorithmName, oldPassword, entity.getSalt(), hashIterations);
        if (!StringUtils.equals(oldHash.toBase64(), entity.getPassword())) {
        	return 0;
        }
        SimpleHash newHash = new SimpleHash(algorithmName, password, entity.getSalt(), hashIterations);
        entity.setPassword(newHash.toBase64());
		return getBaseMapper().updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doAllot(AuthzUserRoleEntity model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(AuthzUserRoleEntity model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AuthzRoleEntity> getRoles(String userId) {
		return getBaseMapper().getRoles(userId);
	}

	@Override
	public List<String> getPermissions(String userId) {
		return getBaseMapper().getPermissions(userId);
	}

	@Override
	public Page<AuthzRoleEntity> getPagedAllocatedList(AuthzUserEntity model) {

		Page<AuthzRoleEntity> page = new Page<AuthzRoleEntity>(model.getPageNo(), model.getLimit());
		if(!CollectionUtils.isEmpty(model.getOrders())) {
			for (OrderItem orderBy : model.getOrders()) {
				page.addOrder(orderBy);
			}
		}

		List<AuthzRoleEntity> records = getBaseMapper().getPagedAllocatedList(page, model);
		page.setRecords(records);

		return page;

	}

	@Override
	public Page<AuthzRoleEntity> getPagedUnAllocatedList(AuthzUserEntity model) {

		Page<AuthzRoleEntity> page = new Page<AuthzRoleEntity>(model.getPageNo(), model.getLimit());
		if(!CollectionUtils.isEmpty(model.getOrders())) {
			for (OrderItem orderBy : model.getOrders()) {
				page.addOrder(orderBy);
			}
		}

		List<AuthzRoleEntity> records = getBaseMapper().getPagedUnAllocatedList(page, model);
		page.setRecords(records);

		return page;
	}

	public AuthzFeatureMapper getFeatureMapper() {
		return featureMapper;
	}
	
	public AuthzRoleMapper getRoleMapper() {
		return roleMapper;
	}
	
	public AuthzUserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}

}
