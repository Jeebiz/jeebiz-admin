/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.monitor.dao.entities.LastOnlineSessionModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface ILastOnlineSessionDao extends BaseDao<LastOnlineSessionModel> {
	
	
}
