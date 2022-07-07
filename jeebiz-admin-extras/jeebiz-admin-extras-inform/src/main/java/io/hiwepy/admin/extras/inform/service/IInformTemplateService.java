/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service;

import java.util.List;

import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplatePairDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.IBaseService;

public interface IInformTemplateService extends IBaseService<InformTemplateEntity> {

	/**
	 * 消息通知模板下拉列表
	 */
	List<InformTemplatePairDTO> getPairs();
	/**
	 * 消息通知模板统计信息
	 */
	List<InformTemplateStatsDTO> getStats();
	
}
