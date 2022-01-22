/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.monitor.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.monitor.dao.entities.SessionEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface SessionMapper extends BaseMapper<SessionEntity> {


}
