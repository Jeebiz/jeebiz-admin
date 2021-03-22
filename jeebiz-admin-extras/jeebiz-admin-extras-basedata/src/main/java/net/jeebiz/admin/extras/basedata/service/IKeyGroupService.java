/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.service;

import java.util.List;

import net.jeebiz.admin.extras.basedata.dao.entities.KeyGroupModel;
import net.jeebiz.boot.api.service.BaseService;

public interface IKeyGroupService extends BaseService<KeyGroupModel> {

	/**
	 * 查询分组数据
	 * @return
	 */
	public List<KeyGroupModel> getPairValues();
	
}
