/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.ops.setup.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import hitool.core.lang3.RandomString;
import hitool.core.lang3.time.DateUtils;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.ops.dao.DayOpsStatsMapper;
import net.jeebiz.admin.extras.ops.dao.entities.DayOpsStatsEntity;
import net.jeebiz.admin.extras.ops.web.dto.DayOpsStatsDTO;

@Configuration
@EnableScheduling
@Slf4j
public class QuartzConfiguration implements InitializingBean {

	private RandomString randomString = new RandomString(7);
	
	@Autowired
	private DayOpsStatsMapper dayOpsStatsMapper;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		 
	}
	   
	/**
	 * 每5分钟同步一次Redis中的统计数据到数据库
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void dataSync() {
		
		String day = DateUtils.getDate("yyyy-MM-dd");
		
		DayOpsStatsDTO statsDTO = getDayOpsStatsMapper().getDayOpsStats();
		DayOpsStatsEntity entity = new DayOpsStatsEntity();
		entity.setDr(statsDTO.getDayRegisterTotal());
		entity.setDau(statsDTO.getDayLoginTotal());
		entity.setNumber(statsDTO.getDayPayNumber());
		entity.setTotal(statsDTO.getDayPayTotal());
		entity.setAmount(statsDTO.getDayPayAmount());
		entity.setDay(day);
		
		Long count = getDayOpsStatsMapper().selectCount(new QueryWrapper<DayOpsStatsEntity>().eq("DAY", day));
		if(count > 0) {
	        entity.setModifyTime(LocalDateTime.now());
			getDayOpsStatsMapper().update(entity, new UpdateWrapper<DayOpsStatsEntity>().eq("DAY", day));
		} else {
	        entity.setCreateTime(LocalDateTime.now());
			getDayOpsStatsMapper().insert(entity);
		}
		
	}
	 
	
	public DayOpsStatsMapper getDayOpsStatsMapper() {
		return dayOpsStatsMapper;
	}
	
}
