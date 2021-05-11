/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.service;

import java.util.List;

import net.jeebiz.admin.extras.settings.dao.entities.SettingsModel;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.IBaseMapperService;

public interface ISettingsService extends IBaseMapperService<SettingsModel> {
	
	/**
	 * 根据分组查询该分组下的系统参数
	 * @param gkey 分组标记
	 * @return
	 */
	public List<PairModel> getPairValues(String gkey);
	
}
