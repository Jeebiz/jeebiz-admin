/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import io.hiwepy.admin.extras.inform.dao.InformEventMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.service.IInformEventService;
import io.hiwepy.admin.extras.inform.web.dto.InformEventStatsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformEventServiceImpl extends BaseServiceImpl<InformEventMapper, InformEventEntity>
		implements IInformEventService {

	@Override
	public List<InformEventStatsDTO> getStats() {
		return getBaseMapper().getStats();
	}

}
