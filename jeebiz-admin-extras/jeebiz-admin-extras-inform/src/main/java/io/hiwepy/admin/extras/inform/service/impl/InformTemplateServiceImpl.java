/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplatePairDTO;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.inform.dao.InformTemplateMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformTemplateEntity;
import io.hiwepy.admin.extras.inform.service.IInformTemplateService;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class InformTemplateServiceImpl extends BaseServiceImpl<InformTemplateMapper, InformTemplateEntity>
		implements IInformTemplateService {

	@Autowired
	private RedisOperationTemplate redisOperation;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(InformTemplateEntity entity) {
		boolean rt = super.save(entity);
		String redisKey = BizRedisKey.INFORM_TEMPLATE.getKey(entity.getId());
		redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
		return rt;
	}

	@Override
	public boolean removeByIds(Collection<?> list) {
		boolean rt = super.removeByIds(list);
		if(rt && !CollectionUtils.isEmpty(list)){
			List<String> keys = list.stream().map(id ->BizRedisKey.INFORM_TEMPLATE.getKey(id)).collect(Collectors.toList());
			redisOperation.getOperations().delete(keys);
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(InformTemplateEntity entity) {
		boolean rt = super.updateById(entity);
		String redisKey = BizRedisKey.INFORM_TEMPLATE.getKey(entity.getId());
		redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
		return rt;
	}

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
