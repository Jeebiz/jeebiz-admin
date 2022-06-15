/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.admin.extras.inform.dao.InformEventMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.service.IInformEventService;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformEventStatsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InformEventServiceImpl extends BaseServiceImpl<InformEventMapper, InformEventEntity>
		implements IInformEventService {

	@Override
	public Long getCount(InformEventEntity entity) {
		if(Objects.nonNull(entity.getId())){
			return getBaseMapper().selectCount(new LambdaQueryWrapper<InformEventEntity>()
					.eq(InformEventEntity::getType, entity.getType())
					.eq(InformEventEntity::getChannel, entity.getChannel())
					.eq(InformEventEntity::getTemplateId, entity.getTemplateId())
					.eq(InformEventEntity::getIsDelete, Constants.Normal.IS_DELETE_0)
					.ne(InformEventEntity::getId, entity.getId()));
		}
		return getBaseMapper().selectCount(new LambdaQueryWrapper<InformEventEntity>()
				.eq(InformEventEntity::getType, entity.getType())
				.eq(InformEventEntity::getChannel, entity.getChannel())
				.eq(InformEventEntity::getTemplateId, entity.getTemplateId())
				.eq(InformEventEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
	}

	@Override
	public List<InformEventStatsDTO> getStats() {
		return getBaseMapper().getStats();
	}

}
