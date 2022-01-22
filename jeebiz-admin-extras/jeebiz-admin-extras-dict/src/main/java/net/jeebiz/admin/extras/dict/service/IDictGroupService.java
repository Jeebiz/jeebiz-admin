/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service;

import java.util.List;

import net.jeebiz.admin.extras.dict.dao.entities.DictGroupEntity;
import net.jeebiz.boot.api.service.IBaseService;

public interface IDictGroupService extends IBaseService<DictGroupEntity> {

	/**
	 * 查询分组数据
	 * @return
	 */
	public List<DictGroupEntity> getKeyGroupList();
	
}
