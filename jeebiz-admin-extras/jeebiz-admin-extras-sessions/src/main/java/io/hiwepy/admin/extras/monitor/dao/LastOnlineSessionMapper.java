/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.monitor.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.monitor.dao.entities.LastOnlineSessionModel;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface LastOnlineSessionMapper extends BaseMapper<LastOnlineSessionModel> {
	
	
}
