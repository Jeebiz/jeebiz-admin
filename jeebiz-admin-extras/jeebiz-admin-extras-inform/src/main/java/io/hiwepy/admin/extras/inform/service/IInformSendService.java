/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service;

import io.hiwepy.admin.extras.inform.dao.entities.InformRecordEntity;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordStatsDTO;
import io.hiwepy.boot.api.service.IBaseService;

import java.util.List;

public interface IInformSendService extends IBaseService<InformRecordEntity> {
	
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
