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
import net.jeebiz.admin.extras.inform.web.vo.InformRecordStatsVo;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class InformRecordServiceImpl extends BaseServiceImpl<InformRecordModel, IInformRecordDao>
		implements IInformRecordService {

	@Override
	public List<InformRecordStatsVo> getStats(String uid) {
		return getDao().getStats(uid);
	}

	@Override
	public int deleteByUid(String uid, List<String> ids) {
		return getDao().deleteByUid(uid, ids);
	}

}
