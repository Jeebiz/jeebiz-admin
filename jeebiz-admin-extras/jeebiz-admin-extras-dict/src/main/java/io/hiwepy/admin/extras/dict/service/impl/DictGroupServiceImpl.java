/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.dict.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.dict.dao.DictGroupMapper;
import io.hiwepy.admin.extras.dict.dao.entities.DictGroupEntity;
import io.hiwepy.admin.extras.dict.service.IDictGroupService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class DictGroupServiceImpl extends BaseServiceImpl<DictGroupMapper, DictGroupEntity> implements IDictGroupService {
	
	@Override
	public List<DictGroupEntity> getKeyGroupList() {
		return getBaseMapper().getKeyGroupList();
	}

}
