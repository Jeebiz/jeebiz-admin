/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.config.dao.BizLogMapper;
import io.hiwepy.admin.extras.config.dao.entities.BizLogEntity;
import io.hiwepy.admin.extras.config.service.IBizLogService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

/**
 * 业务操作日志Service实现
 */
@Service
public class BizLogServiceImpl extends BaseServiceImpl<BizLogMapper, BizLogEntity> implements IBizLogService {

}
