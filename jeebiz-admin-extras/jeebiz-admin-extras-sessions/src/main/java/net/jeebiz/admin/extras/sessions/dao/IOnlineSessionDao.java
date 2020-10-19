/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.sessions.dao.entities.OnlineSessionModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IOnlineSessionDao extends BaseDao<OnlineSessionModel> {
	
	
}
