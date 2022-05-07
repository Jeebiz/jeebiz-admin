/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.inform.dao.InformTemplateMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.service.IInformTemplateService;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class InformTemplateServiceImpl extends BaseServiceImpl<InformTemplateMapper, InformTemplateEntity>
		implements IInformTemplateService {

	@Override
	public List<InformTemplateStatsDTO> getStats() {
		return getBaseMapper().getStats();
	}

}
