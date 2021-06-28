/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.jeebiz.admin.extras.monitor.dao.entities.CpuInfoEntity;
import net.jeebiz.admin.extras.monitor.dao.entities.MemInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemInfoDao extends BaseMapper<MemInfoEntity> {
		
}
