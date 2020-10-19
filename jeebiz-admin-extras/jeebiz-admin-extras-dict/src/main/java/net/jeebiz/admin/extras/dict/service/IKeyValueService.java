/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service;

import java.util.List;
import java.util.Map;

import net.jeebiz.admin.extras.dict.dao.entities.KeyValueModel;
import net.jeebiz.boot.api.service.IBaseService;

public interface IKeyValueService extends IBaseService<KeyValueModel> {

	public Map<String, List<KeyValueModel>> getGroupPairValues(String[] gkeys);
	
}
