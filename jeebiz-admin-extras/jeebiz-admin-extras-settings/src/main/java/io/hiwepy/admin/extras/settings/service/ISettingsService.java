/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.settings.service;

import java.util.List;

import io.hiwepy.admin.extras.settings.dao.entities.SettingsModel;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.IBaseService;

public interface ISettingsService extends IBaseService<SettingsModel> {
	
	/**
	 * 根据分组查询该分组下的系统参数
	 * @param gkey 分组标记
	 * @return
	 */
	public List<PairModel> getPairValues(String gkey);
	
}
