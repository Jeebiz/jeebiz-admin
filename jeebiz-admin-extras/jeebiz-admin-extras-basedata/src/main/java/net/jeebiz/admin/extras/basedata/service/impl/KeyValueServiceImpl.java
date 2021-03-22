/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.basedata.dao.IKeyValueDao;
import net.jeebiz.admin.extras.basedata.dao.entities.KeyValueModel;
import net.jeebiz.admin.extras.basedata.service.IKeyValueService;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class KeyValueServiceImpl extends BaseServiceImpl<KeyValueModel, IKeyValueDao> implements IKeyValueService {
	
	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getDao().getPairValues(gkey);
	}

}
