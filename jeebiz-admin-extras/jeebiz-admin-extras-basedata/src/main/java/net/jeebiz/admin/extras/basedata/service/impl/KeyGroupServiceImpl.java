/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.basedata.dao.IKeyGroupDao;
import net.jeebiz.admin.extras.basedata.dao.entities.KeyGroupModel;
import net.jeebiz.admin.extras.basedata.service.IKeyGroupService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class KeyGroupServiceImpl extends BaseServiceImpl<KeyGroupModel, IKeyGroupDao> implements IKeyGroupService {
	
	@Override
	public List<KeyGroupModel> getPairValues() {
		return getDao().getPairValues();
	}

}
