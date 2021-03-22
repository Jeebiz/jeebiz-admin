/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.authz.org.dao.IAuthzStaffDao;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzStaffModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzStaffService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzStaffServiceImpl extends BaseServiceImpl<AuthzStaffModel, IAuthzStaffDao> implements IAuthzStaffService{

}
