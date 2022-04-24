/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service.impl;


import net.jeebiz.admin.authz.rbac0.dao.UserRoleMapper;
import net.jeebiz.admin.authz.rbac0.dao.entities.UserRoleEntity;
import net.jeebiz.admin.authz.rbac0.service.IUserRoleService;
import net.jeebiz.boot.api.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRoleEntity>
		implements IUserRoleService {

}
