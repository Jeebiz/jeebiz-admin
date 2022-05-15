/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service.impl;

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
import io.hiwepy.admin.authz.feature.dao.FeatureMapper;
import io.hiwepy.admin.authz.rbac0.dao.RoleMapper;
import io.hiwepy.admin.authz.rbac0.dao.UserAccountMapper;
import io.hiwepy.admin.authz.rbac0.dao.UserRoleMapper;
import io.hiwepy.admin.authz.rbac0.dao.entities.AccountStatusModel;
import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserRoleEntity;
import io.hiwepy.admin.authz.rbac0.service.IUserAccountService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountMapper, UserAccountEntity> implements IUserAccountService {

	protected RandomString randomString = new RandomString(8);

	@Autowired
	private FeatureMapper featureMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	// 加密方式
	private String algorithmName = "MD5";
    // 加密的次数,可以进行多次的加密操作
    private int hashIterations = 10;

	@Override
	public List<UserAccountEntity> getUserList() {
		return getBaseMapper().getUserList();
	}

	@Override
	public int setStatus(String userId, String status) {
		return getBaseMapper().setStatus(userId, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(UserAccountEntity model) {

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
		getUserRoleMapper().delete(new QueryWrapper<UserRoleEntity>().eq("user_id", id));
		return getBaseMapper().deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(UserAccountEntity model) {
		int ct = getBaseMapper().updateById(model);
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int resetPwd(String userId, String oldPassword, String password) {
		// 查询用户信息
		UserAccountEntity entity = getBaseMapper().selectById(userId);
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
	public int doAllot(UserRoleEntity model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int doUnAllot(UserRoleEntity model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AccountStatusModel getAccountStatus(String account, String password) {
		return getBaseMapper().getAccountStatus(account, password);
	}

	@Override
	public AccountStatusModel getAccountStatusWithoutPwd(String account) {
		return getBaseMapper().getAccountStatusWithoutPwd(account);
	}

	@Override
	public AccountStatusModel getAccountStatusByUserId(String userId) {
		return getBaseMapper().getAccountStatusByUserId(userId);
	}

	@Override
	public UserAccountEntity getAccountByType(String type, String account) {
		return getBaseMapper().selectOne(new QueryWrapper<UserAccountEntity>().eq("type", type).eq("account", account));
	}

	@Override
	public UserAccountEntity getAccountById(String id) {
		return getBaseMapper().selectById(id);
	}

	@Override
	public UserAccountEntity getAccountByUcode(String userCode) {
		return getBaseMapper().selectOne(new QueryWrapper<UserAccountEntity>().eq("user_code", userCode));
	}

	@Override
	public UserAccountEntity getAccountByUserId(String userId) {
		return getBaseMapper().selectOne(new QueryWrapper<UserAccountEntity>().eq("user_id", userId));
	}

	@Override
	public List<String> getRoleKeys(String userId) {
		return getBaseMapper().getRoleKeys(userId);
	}

	@Override
	public List<RoleEntity> getRoles(String userId) {
		return getBaseMapper().getRoles(userId);
	}

	@Override
	public List<String> getPermissions(String userId) {
		return getBaseMapper().getPermissions(userId);
	}

	@Override
	public Page<RoleEntity> getPagedAllocatedList(UserAccountEntity model) {

		Page<RoleEntity> page = new Page<RoleEntity>(model.getPageNo(), model.getLimit());
		if(!CollectionUtils.isEmpty(model.getOrders())) {
			for (OrderItem orderBy : model.getOrders()) {
				page.addOrder(orderBy);
			}
		}

		List<RoleEntity> records = getBaseMapper().getPagedAllocatedList(page, model);
		page.setRecords(records);

		return page;

	}

	@Override
	public Page<RoleEntity> getPagedUnAllocatedList(UserAccountEntity model) {

		Page<RoleEntity> page = new Page<RoleEntity>(model.getPageNo(), model.getLimit());
		if(!CollectionUtils.isEmpty(model.getOrders())) {
			for (OrderItem orderBy : model.getOrders()) {
				page.addOrder(orderBy);
			}
		}

		List<RoleEntity> records = getBaseMapper().getPagedUnAllocatedList(page, model);
		page.setRecords(records);

		return page;
	}

	public FeatureMapper getFeatureMapper() {
		return featureMapper;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public UserRoleMapper getUserRoleMapper() {
		return userRoleMapper;
	}

}
