/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.dict.dao.DictGroupMapper;
import net.jeebiz.admin.extras.dict.dao.entities.DictGroupEntity;
import net.jeebiz.admin.extras.dict.service.IDictGroupService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class DictGroupServiceImpl extends BaseServiceImpl<DictGroupMapper, DictGroupEntity> implements IDictGroupService {
	
	@Override
	public List<DictGroupEntity> getKeyGroupList() {
		return getBaseMapper().getKeyGroupList();
	}

}
