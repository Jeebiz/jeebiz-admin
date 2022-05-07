/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.AuthzPostMapper;
import io.hiwepy.admin.authz.org.dao.entities.AuthzPostModel;
import io.hiwepy.admin.authz.org.service.IAuthzPostService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class AuthzPostServiceImpl extends BaseServiceImpl<AuthzPostMapper, AuthzPostModel> implements IAuthzPostService{

}
