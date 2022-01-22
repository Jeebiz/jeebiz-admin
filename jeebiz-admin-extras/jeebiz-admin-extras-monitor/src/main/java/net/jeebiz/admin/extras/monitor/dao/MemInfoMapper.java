/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.jeebiz.admin.extras.monitor.dao.entities.MemInfoEntity;

@Mapper
public interface MemInfoMapper extends BaseMapper<MemInfoEntity> {
		
}
