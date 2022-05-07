/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.monitor.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.hiwepy.admin.extras.monitor.dao.entities.DiskInfoEntity;

@Mapper
public interface DiskInfoMapper extends BaseMapper<DiskInfoEntity> {
		
}
