/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service;

import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.web.dto.InformEventStatsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformEventTargetDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformEventTargetsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.IBaseService;

import java.util.List;

public interface IInformEventService extends IBaseService<InformEventEntity> {
	
	/**
	 * 消息通知事件统计信息
	 */
	List<InformEventStatsDTO> getStats();

    boolean setTargets(InformEventTargetsDTO targetDtos);

	InformEventTargetsDTO getTargets(String eventId);
}
