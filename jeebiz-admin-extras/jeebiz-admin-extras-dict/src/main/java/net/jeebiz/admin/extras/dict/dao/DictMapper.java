/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.boot.api.dao.BaseMapper;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Mapper
public interface DictMapper extends BaseMapper<PairModel> {
	
	
}
