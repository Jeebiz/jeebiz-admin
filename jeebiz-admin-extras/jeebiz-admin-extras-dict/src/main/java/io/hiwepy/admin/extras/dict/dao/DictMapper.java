/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.dict.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;

@Mapper
public interface DictMapper extends BaseMapper<PairModel> {
	
	
}
