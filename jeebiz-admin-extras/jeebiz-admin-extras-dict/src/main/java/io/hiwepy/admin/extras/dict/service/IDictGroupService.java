/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.dict.service;

import java.util.List;

import io.hiwepy.admin.extras.dict.dao.entities.DictGroupEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IDictGroupService extends IBaseService<DictGroupEntity> {

	/**
	 * 查询分组数据
	 * @return
	 */
	public List<DictGroupEntity> getKeyGroupList();
	
}
