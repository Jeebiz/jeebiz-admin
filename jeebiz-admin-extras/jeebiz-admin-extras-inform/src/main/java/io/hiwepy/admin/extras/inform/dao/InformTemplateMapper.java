/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface InformTemplateMapper extends BaseMapper<InformTemplateEntity> {

	/**
	 * 消息通知统计信息
	 */
	List<InformTemplateStatsDTO> getStats();
	
}
