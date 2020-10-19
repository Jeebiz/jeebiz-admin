/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.dict.dao.entities.KeyValueModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IKeyValueDao extends BaseDao<KeyValueModel> {
	
	public List<KeyValueModel> getKeyValueList(@Param("gkeys") List<String> gkeys);
	
	/**
	 * 根据给出的基础数据ID集合查询所属的数据组集合
	 * @param list
	 * @return
	 */
	public List<String> getGroupList(@Param("gkeys") List<?> gkeys);
	
}
