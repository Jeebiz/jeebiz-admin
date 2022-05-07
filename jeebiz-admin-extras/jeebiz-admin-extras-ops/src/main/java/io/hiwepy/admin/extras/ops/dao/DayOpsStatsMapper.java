/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.ops.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.hiwepy.admin.extras.ops.dao.entities.DayOpsStatsEntity;
import io.hiwepy.admin.extras.ops.web.dto.DayOpsStatsDTO;

/**
 * 每日运营统计
 */
@Mapper
public interface DayOpsStatsMapper extends BaseMapper<DayOpsStatsEntity> {
	
	DayOpsStatsDTO getDayOpsStats();
	
}
