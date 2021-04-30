/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.ops.setup.config;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.ops.dao.IDayOpsStatsDao;
import net.jeebiz.admin.extras.ops.dao.entities.DayOpsStatsEntity;
import net.jeebiz.admin.extras.ops.web.dto.DayOpsStatsDTO;
import net.jeebiz.boot.api.utils.RandomString;

@Configuration
@EnableScheduling
@Slf4j
public class QuartzConfiguration implements InitializingBean {

	private RandomString randomString = new RandomString(7);
	
	@Autowired
	private IDayOpsStatsDao dayOpsStatsDao;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		 
	}
	   
	/**
	 * 每5分钟同步一次Redis中的统计数据到数据库
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void dataSync() {
		
		String day = DateUtils.getDate("yyyy-MM-dd");
		
		DayOpsStatsDTO statsDTO = getDayOpsStatsDao().getDayOpsStats();
		DayOpsStatsEntity entity = new DayOpsStatsEntity();
		entity.setDr(statsDTO.getDayRegisterTotal());
		entity.setDau(statsDTO.getDayLoginTotal());
		entity.setNumber(statsDTO.getDayPayNumber());
		entity.setTotal(statsDTO.getDayPayTotal());
		entity.setAmount(statsDTO.getDayPayAmount());
		entity.setDay(day);
		
		Integer count = getDayOpsStatsDao().selectCount(new QueryWrapper<DayOpsStatsEntity>().eq("DAY", day));
		if(count > 0) {
	        entity.setModifyTime(new Date());
			getDayOpsStatsDao().update(entity, new UpdateWrapper<DayOpsStatsEntity>().eq("DAY", day));
		} else {
	        entity.setCreateTime(new Date());
			getDayOpsStatsDao().insert(entity);
		}
		
	}
	 
	
	public IDayOpsStatsDao getDayOpsStatsDao() {
		return dayOpsStatsDao;
	}
	
}
