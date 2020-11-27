/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.ops.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.jeebiz.admin.extras.ops.dao.entities.DayOpsStatsEntity;
import net.jeebiz.admin.extras.ops.web.dto.DayOpsStatsDTO;

/**
 * 每日运营统计
 */
@Mapper
public interface IDayOpsStatsDao extends BaseMapper<DayOpsStatsEntity> {
	
	DayOpsStatsDTO getDayOpsStats();
	
}
