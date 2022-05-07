/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.authz.rbac0.dao.entities.AuthzUserRoleEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

/**
 * 用户角色DAO
 */
@Mapper
public interface AuthzUserRoleMapper extends BaseMapper<AuthzUserRoleEntity>{

}
