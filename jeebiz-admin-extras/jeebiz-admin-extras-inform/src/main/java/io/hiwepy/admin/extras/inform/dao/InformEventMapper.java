/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.dao;


import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.web.dto.InformEventStatsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InformEventMapper extends BaseMapper<InformEventEntity> {

	/**
	 * 消息通知事件统计信息
	 */
	List<InformEventStatsDTO> getStats();
	
}
