/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.authz.org.dao.IAuthzPostDao;
import net.jeebiz.admin.extras.authz.org.dao.entities.AuthzPostModel;
import net.jeebiz.admin.extras.authz.org.service.IAuthzPostService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzPostServiceImpl extends BaseServiceImpl<AuthzPostModel, IAuthzPostDao> implements IAuthzPostService{

}