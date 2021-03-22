/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.service;

import java.util.List;

import net.jeebiz.admin.extras.basedata.dao.entities.KeyValueModel;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseService;

public interface IKeyValueService extends BaseService<KeyValueModel> {

	/**
	 * 根据分组查询该分组下的基础数据
	 * @param gkey 分组标记
	 * @return
	 */
	public List<PairModel> getPairValues(String gkey);
	
}
