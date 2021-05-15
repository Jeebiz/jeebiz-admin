/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.dict.dao.IKeyGroupDao;
import net.jeebiz.admin.extras.dict.dao.entities.KeyGroupModel;
import net.jeebiz.admin.extras.dict.service.IKeyGroupService;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class KeyGroupServiceImpl extends BaseMapperServiceImpl<KeyGroupModel, IKeyGroupDao> implements IKeyGroupService {
	
	@Override
	public List<KeyGroupModel> getKeyGroupList() {
		return getBaseMapper().getKeyGroupList();
	}

}
