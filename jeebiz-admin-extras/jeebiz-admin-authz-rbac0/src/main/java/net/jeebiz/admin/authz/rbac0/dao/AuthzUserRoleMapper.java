/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserRoleEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 用户角色DAO
 */
@Mapper
public interface AuthzUserRoleMapper extends BaseMapper<AuthzUserRoleEntity>{

}
