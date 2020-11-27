/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.inform.dao.IInformTemplateDao;
import net.jeebiz.admin.extras.inform.dao.entities.InformTemplateModel;
import net.jeebiz.admin.extras.inform.service.IInformTemplateService;
import net.jeebiz.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class InformTemplateServiceImpl extends BaseServiceImpl<InformTemplateModel, IInformTemplateDao>
		implements IInformTemplateService {

	@Override
	public List<InformTemplateStatsDTO> getStats() {
		return getDao().getStats();
	}

}
