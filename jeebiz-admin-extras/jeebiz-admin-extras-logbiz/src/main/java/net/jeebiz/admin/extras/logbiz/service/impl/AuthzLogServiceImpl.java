/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.logbiz.dao.IAuthzLogDao;
import net.jeebiz.admin.extras.logbiz.dao.entities.AuthzLogModel;
import net.jeebiz.admin.extras.logbiz.service.IAuthzLogService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

/**
 * 认证授权日志Service实现
 */
@Service
public class AuthzLogServiceImpl extends BaseMapperServiceImpl<AuthzLogModel, IAuthzLogDao>
		implements IAuthzLogService {
 
}
