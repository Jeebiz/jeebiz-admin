/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.settings.dao.ISettingsDao;
import net.jeebiz.admin.extras.settings.dao.entities.SettingsModel;
import net.jeebiz.admin.extras.settings.service.ISettingsService;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class SettingsServiceImpl extends BaseServiceImpl<SettingsModel, ISettingsDao> implements ISettingsService {

	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getDao().getPairValues(gkey);
	}
	
}
