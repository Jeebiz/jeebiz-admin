/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.inform.dao.entities.InformTemplateModel;
import net.jeebiz.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface InformTemplateMapper extends BaseMapper<InformTemplateModel> {

	/**
	 * 消息通知统计信息
	 */
	List<InformTemplateStatsDTO> getStats();
	
}
