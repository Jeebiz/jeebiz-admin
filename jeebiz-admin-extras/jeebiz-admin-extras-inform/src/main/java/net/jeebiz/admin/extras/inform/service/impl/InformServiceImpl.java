/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.inform.dao.IInformDao;
import net.jeebiz.admin.extras.inform.dao.entities.InformModel;
import net.jeebiz.admin.extras.inform.service.IInformService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class InformServiceImpl extends BaseServiceImpl<InformModel, IInformDao> implements IInformService {

	@Override
	public Map<String, String> getStats(String userId) {
		return getDao().getStats(userId);
	}

	@Override
	public int delInforms(String userId, List<String> ids) {
		return getDao().delInforms(userId, ids);
	}
	
}
