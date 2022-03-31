/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.inform.dao.entities.InformTargetEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface InformTargetMapper extends BaseMapper<InformTargetEntity> {

	int deleteByTid(@Param("tid") String tid);
	
}
