/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.service.impl;


import org.springframework.stereotype.Service;

import net.jeebiz.admin.authz.thirdparty.dao.IAuthzThirdpartyUserDao;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyUserModel;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyUserService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class AuthzThirdpartyServiceUserImpl extends BaseMapperServiceImpl<AuthzThirdpartyUserModel, IAuthzThirdpartyUserDao>
		implements IAuthzThirdpartyUserService {
	
}
