/**
 * Copyright (C) 2018 Jeebiz (http://dajuxiaowo.com).
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.authz.principal.ShiroPrincipalRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.hiwepy.jwt.JwtPayload.RolePair;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;
import io.hiwepy.admin.authz.rbac0.dao.entities.RoleEntity;
import io.hiwepy.admin.authz.rbac0.service.IRolePermsService;
import io.hiwepy.admin.authz.rbac0.service.IRoleService;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.admin.shadow.setup.strategy.AuthStrategyRouter;

@Service("defRepository")
@Slf4j
public class AuthzPrincipalRepositoryImpl extends ShiroPrincipalRepositoryImpl {

 	@Autowired
	private AuthStrategyRouter authStrategyRouter;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IRolePermsService rolePermsService;
	@Autowired
	private RedisOperationTemplate redisOperation;
	
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("{} >> getAuthenticationInfo token:{}", token.getClass(), JSONObject.toJSONString(token, SerializerFeature.WriteNonStringValueAsString));
		return authStrategyRouter.route(token).login(token);
	}

	@Override
	public Set<String> getRoles(Set<Object> principals) {
		Set<String> sets =  Sets.newHashSet();
		for (Object principal : principals) {
			if(principal instanceof ShiroPrincipal) {
				ShiroPrincipal shiroPrincipal = (ShiroPrincipal) principal;
				// 用户角色信息集合
				String userRolesKey = BizRedisKey.USER_ROLES.getKey(shiroPrincipal.getUserid());
				
				Map<String, Object> roleCache = redisOperation.hmGet(userRolesKey);
				if(MapUtils.isNotEmpty(roleCache)) {
					List<RoleEntity> roleModels = roleCache.entrySet().stream().map(entry -> {
						if(entry.getValue() instanceof RoleEntity) {
							return (RoleEntity) entry.getValue();
						}
						return JSONObject.parseObject(JSONObject.toJSONString(entry.getValue()), RoleEntity.class);
					}).collect(Collectors.toList());
					for (RoleEntity roleEntity : roleModels) {
						sets.add(roleEntity.getKey());
					}
					
				}
				
			}
		}
		return sets;
	}


	@Override
	public Set<String> getPermissions(Set<Object> principals) {
		Set<String> set =  Sets.newHashSet();
		for (Object principal : principals) {
			if(principal instanceof ShiroPrincipal) {
				ShiroPrincipal model = (ShiroPrincipal) principal;
				String userPermsKey = BizRedisKey.USER_PERMS.getKey(model.getRoleid());
				
				set.addAll(getRolePermsService().getPermissions(model.getRoleid()));
				for (RolePair role : model.getRoles()) {
					set.addAll(getRolePermsService().getPermissions(role.getId()));
				}
			}
		}
		return set;
	}

	@Override
	public void doLock(Object principal) {
		// TODO Auto-generated method stub

	}
	
	public IRoleService getRoleService() {
		return roleService;
	}
	
	public IRolePermsService getRolePermsService() {
		return rolePermsService;
	}

}
