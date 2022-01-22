/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.service;

import java.util.List;

import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.web.dto.InformRecordStatsDTO;
import net.jeebiz.boot.api.service.IBaseService;

public interface IInformRecordService extends IBaseService<InformRecordModel> {
	
	/**
	 * 消息通知统计信息
	 */
	List<InformRecordStatsDTO> getStats(String uid);

	/**
	 * 删除用户的通知信息
	 * @param userId
	 * @param ids
	 * @return
	 */
	int deleteByUid( String uid,  List<String> ids);
	
}
