/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.logbiz.dao.BizLogMapper;
import io.hiwepy.admin.extras.logbiz.dao.entities.BizLogModel;
import io.hiwepy.admin.extras.logbiz.service.IBizLogService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

/**
 * 业务操作日志Service实现
 */
@Service
public class BizLogServiceImpl extends BaseServiceImpl<BizLogMapper, BizLogModel> implements IBizLogService {

}