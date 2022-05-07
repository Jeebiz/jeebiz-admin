/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.inform.dao.InformRecordMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformRecordEntity;
import io.hiwepy.admin.extras.inform.service.IInformRecordService;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordStatsDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class InformRecordServiceImpl extends BaseServiceImpl<InformRecordMapper, InformRecordEntity>
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
