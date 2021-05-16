/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.service.impl;


import org.springframework.stereotype.Service;

import net.jeebiz.admin.authz.thirdparty.dao.IAuthzThirdpartyUserProfileDao;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyUserProfileModel;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyUserProfileService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class AuthzThirdpartyServiceUserProfileImpl extends BaseMapperServiceImpl<AuthzThirdpartyUserProfileModel, IAuthzThirdpartyUserProfileDao>
		implements IAuthzThirdpartyUserProfileService {
	
}
