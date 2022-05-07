/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.dict.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.extras.dict.dao.entities.DictPairEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface DictPairMapper extends BaseMapper<DictPairEntity> {

	public List<DictPairEntity> getDictPairList(@Param("gkeys") List<String> gkeys);

	/**
	 * 根据给出的基础数据id集合查询所属的数据组集合
	 * @param gkeys
	 * @return
	 */
	public List<String> getGroupList(@Param("gkeys") Collection<?> gkeys);

}
