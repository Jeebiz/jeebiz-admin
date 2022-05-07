/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.dict.service;

import java.util.List;
import java.util.Map;

import io.hiwepy.admin.extras.dict.dao.entities.DictPairEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface IDictPairService extends IBaseService<DictPairEntity> {

	public Map<String, List<DictPairEntity>> getGroupPairValues(String[] gkeys);
	
}
