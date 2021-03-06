/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.inform.dao.IInformRecordDao;
import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.service.IInformRecordService;
import net.jeebiz.admin.extras.inform.web.dto.InformRecordStatsDTO;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;

@Service
public class InformRecordServiceImpl extends BaseMapperServiceImpl<InformRecordModel, IInformRecordDao>
		implements IInformRecordService {

	@Override
	public List<InformRecordStatsDTO> getStats(String uid) {
		return getBaseMapper().getStats(uid);
	}

	@Override
	public int deleteByUid(String uid, List<String> ids) {
		return getBaseMapper().deleteByUid(uid, ids);
	}

}
