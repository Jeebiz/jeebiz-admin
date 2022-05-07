/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service.impl;


import io.hiwepy.admin.authz.rbac0.dao.UserRoleMapper;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserRoleEntity;
import io.hiwepy.admin.authz.rbac0.service.IUserRoleService;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRoleEntity>
		implements IUserRoleService {

}
