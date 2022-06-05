/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.tree.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.tree.dao.AuthzLogMapper;
import io.hiwepy.admin.extras.tree.dao.entities.AuthzLogEntity;
import io.hiwepy.admin.extras.tree.service.IAuthzLogService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

/**
 * 认证授权日志Service实现
 */
@Service
public class AuthzLogServiceImpl extends BaseServiceImpl<AuthzLogMapper, AuthzLogEntity> implements IAuthzLogService {
 
}
