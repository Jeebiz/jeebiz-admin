/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.monitor.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.hiwepy.admin.extras.monitor.dao.entities.MemInfoEntity;

@Mapper
public interface MemInfoMapper extends BaseMapper<MemInfoEntity> {
		
}
