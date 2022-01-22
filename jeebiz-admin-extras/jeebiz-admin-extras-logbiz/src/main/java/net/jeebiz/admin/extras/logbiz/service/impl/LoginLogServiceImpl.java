/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.logbiz.dao.LoginLogMapper;
import net.jeebiz.admin.extras.logbiz.dao.entities.LoginLogModel;
import net.jeebiz.admin.extras.logbiz.service.ILoginLogService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

/**
 * 认证授权日志Service实现
 */
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper, LoginLogModel> implements ILoginLogService {
 
}
