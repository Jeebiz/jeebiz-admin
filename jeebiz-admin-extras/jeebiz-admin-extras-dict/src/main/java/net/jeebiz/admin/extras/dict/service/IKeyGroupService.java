/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service;

import java.util.List;

import net.jeebiz.admin.extras.dict.dao.entities.KeyGroupModel;
import net.jeebiz.boot.api.service.IBaseMapperService;

public interface IKeyGroupService extends IBaseMapperService<KeyGroupModel> {

	/**
	 * 查询分组数据
	 * @return
	 */
	public List<KeyGroupModel> getKeyGroupList();
	
}
