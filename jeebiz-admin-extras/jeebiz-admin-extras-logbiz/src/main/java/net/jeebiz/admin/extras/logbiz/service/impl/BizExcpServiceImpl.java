/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.logbiz.dao.IBizExcpDao;
import net.jeebiz.admin.extras.logbiz.dao.entities.BizExcpModel;
import net.jeebiz.admin.extras.logbiz.service.IBizExcpService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

/**
 * 系统异常日志Service实现
 */
@Service
public class BizExcpServiceImpl extends BaseMapperServiceImpl<BizExcpModel, IBizExcpDao>
		implements IBizExcpService {

	@Override
	public List<Map<String, String>> getExcpTypes() {
		return getDao().getExcpTypes();
	}
 
}
