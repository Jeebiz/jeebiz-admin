/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.service;

import java.util.List;
import java.util.Map;

import net.jeebiz.admin.extras.logbiz.dao.entities.BizExcpModel;
import net.jeebiz.boot.api.service.BaseService;

/**
 * 系统异常日志Service
 */
public interface IBizExcpService extends BaseService<BizExcpModel>{

	List<Map<String, String>> getExcpTypes();
	
}
