/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.dict.dao.entities.DictGroupEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface DictGroupMapper extends BaseMapper<DictGroupEntity> {

	/**
	 * 查询分组数据
	 * @return
	 */
	public List<DictGroupEntity> getKeyGroupList();
	
}
