/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplatePairDTO;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.inform.dao.InformTemplateMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.service.IInformTemplateService;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.util.CollectionUtils;

@Service
public class InformTemplateServiceImpl extends BaseServiceImpl<InformTemplateMapper, InformTemplateEntity>
		implements IInformTemplateService {

	@Override
	public List<InformTemplatePairDTO> getPairs() {

		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);

		List<InformTemplateEntity> templateList = getBaseMapper().selectList(new LambdaQueryWrapper<InformTemplateEntity>()
				.eq(InformTemplateEntity::getStatus, Constants.Normal.IS_STATUS_YES)
				.eq(InformTemplateEntity::getCreator, principal.getUserid())
				.eq(InformTemplateEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
		if(CollectionUtils.isEmpty(templateList)){
			return Lists.newArrayList();
		}

		return templateList.stream()
				.map(template -> InformTemplatePairDTO.builder().id(template.getId()).name(template.getName()).build())
				.collect(Collectors.toList());
	}

	@Override
	public List<InformTemplateStatsDTO> getStats() {
		return getBaseMapper().getStats();
	}

}
