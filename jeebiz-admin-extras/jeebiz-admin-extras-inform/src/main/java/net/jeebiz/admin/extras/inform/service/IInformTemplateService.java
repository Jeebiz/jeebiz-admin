/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.service;

import java.util.List;

import net.jeebiz.admin.extras.inform.dao.entities.InformTemplateModel;
import net.jeebiz.admin.extras.inform.web.vo.InformTemplateStatsVo;
import net.jeebiz.boot.api.service.IBaseService;

public interface IInformTemplateService extends IBaseService<InformTemplateModel> {
	
	/**
	 * 消息通知统计信息
	 */
	List<InformTemplateStatsVo> getStats();
	
}
