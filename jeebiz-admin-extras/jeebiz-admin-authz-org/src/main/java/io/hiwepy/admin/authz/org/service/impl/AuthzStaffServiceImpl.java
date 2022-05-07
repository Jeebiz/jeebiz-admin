/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.AuthzStaffMapper;
import io.hiwepy.admin.authz.org.dao.entities.AuthzStaffModel;
import io.hiwepy.admin.authz.org.service.IAuthzStaffService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class AuthzStaffServiceImpl extends BaseServiceImpl<AuthzStaffMapper, AuthzStaffModel> implements IAuthzStaffService{
	
}
