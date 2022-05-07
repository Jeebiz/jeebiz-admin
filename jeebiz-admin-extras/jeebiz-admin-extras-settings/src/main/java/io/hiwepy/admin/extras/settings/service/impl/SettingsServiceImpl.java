/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.settings.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.settings.dao.SettingsMapper;
import io.hiwepy.admin.extras.settings.dao.entities.SettingsModel;
import io.hiwepy.admin.extras.settings.service.ISettingsService;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class SettingsServiceImpl extends BaseServiceImpl<SettingsMapper, SettingsModel> implements ISettingsService {

	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getBaseMapper().getPairValues(gkey);
	}
	
}
