/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.admin.extras.inform.dao.InformEventMapper;
import io.hiwepy.admin.extras.inform.dao.entities.InformEventEntity;
import io.hiwepy.admin.extras.inform.dao.entities.InformTargetEntity;
import io.hiwepy.admin.extras.inform.emums.InformTarget;
import io.hiwepy.admin.extras.inform.service.IInformEventService;
import io.hiwepy.admin.extras.inform.service.IInformTargetService;
import io.hiwepy.admin.extras.inform.setup.Constants;
import io.hiwepy.admin.extras.inform.web.dto.InformEventStatsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformEventTargetDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformEventTargetsDTO;
import io.hiwepy.admin.extras.inform.web.dto.InformTemplateStatsDTO;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InformEventServiceImpl extends BaseServiceImpl<InformEventMapper, InformEventEntity>
		implements IInformEventService {

	@Autowired
	private IInformTargetService informTargetService;
	@Autowired
	private RedisOperationTemplate redisOperation;


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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(InformEventEntity entity) {
		boolean rt = super.save(entity);
		String redisKey = BizRedisKey.INFORM_EVENT.getKey(entity.getId());
		redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
		return rt;
	}

	@Override
	public boolean removeByIds(Collection<?> list) {
		boolean rt = super.removeByIds(list);
		if(rt && !CollectionUtils.isEmpty(list)){
			List<String> keys = list.stream().map(id ->BizRedisKey.INFORM_EVENT.getKey(id)).collect(Collectors.toList());
			redisOperation.getOperations().delete(keys);
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(InformEventEntity entity) {
		boolean rt = super.updateById(entity);
		String redisKey = BizRedisKey.INFORM_EVENT.getKey(entity.getId());
		redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean setTargets(InformEventTargetsDTO targetsDto) {

		// 1、设置消息事件的通知对象类型
		InformEventEntity eventEntity = new InformEventEntity();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		eventEntity.setId(targetsDto.getEventId());
		eventEntity.setModifyer(principal.getUserid());
		eventEntity.setTarget(targetsDto.getTarget());
		getBaseMapper().updateById(eventEntity);

		String redisKey = BizRedisKey.INFORM_EVENT.getKey(eventEntity.getId());
		redisOperation.hSet(redisKey, "target", targetsDto.getTarget());

		// 2、查询已经设置的通知对象并删除旧的通知对象
		this.removeTargetsByEventId(targetsDto.getEventId());

		// 3、如果设置的是部分通知，则进行通知对象处理
		if(InformTarget.SPECIFIC.equals(targetsDto.getTarget()) && !CollectionUtils.isEmpty(targetsDto.getTargets())){
			targetsDto.getTargets().stream().forEach(targetDto -> {
				InformTargetEntity entity = new InformTargetEntity();
				entity.setEventId(targetsDto.getEventId());
				entity.setToType(targetDto.getToType());
				entity.setTargetId(targetDto.getTargetId());
				informTargetService.save(entity);

				String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetDto.getToType(), eventEntity.getId());
				redisOperation.zAdd(targetRedisKey, targetDto.getTargetId(), System.currentTimeMillis());

			});
		}

		return true;
	}

	protected List<InformTargetEntity> removeTargetsByEventId(String eventId){
		List<InformTargetEntity> targets = informTargetService.list(new LambdaQueryWrapper<InformTargetEntity>()
				.select(InformTargetEntity::getId, InformTargetEntity::getToType, InformTargetEntity::getTargetId)
				.eq(InformTargetEntity::getEventId, eventId));
		if(!CollectionUtils.isEmpty(targets)){
			targets.stream().forEach(targetEntity -> {
				String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetEntity.getToType(), eventId);
				redisOperation.zRem(targetRedisKey, targetEntity.getTargetId());
			});
			informTargetService.remove(new LambdaQueryWrapper<InformTargetEntity>().eq(InformTargetEntity::getEventId, eventId));
		}
		return targets;
	}

	@Override
	public InformEventTargetsDTO getTargets(String eventId) {
		InformEventTargetsDTO targetsDTO = new InformEventTargetsDTO();
		InformEventEntity entity = getBaseMapper().selectById(eventId);
		if(Objects.isNull(entity)){
			return targetsDTO;
		}
		targetsDTO.setTarget(entity.getTarget());
		targetsDTO.setEventId(eventId);
		List<InformTargetEntity> targets = informTargetService.list(new LambdaQueryWrapper<InformTargetEntity>()
				.select(InformTargetEntity::getId, InformTargetEntity::getToType, InformTargetEntity::getTargetId)
				.eq(InformTargetEntity::getEventId, eventId)
				.eq(InformTargetEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
		if(!CollectionUtils.isEmpty(targets)){
			targetsDTO.setTargets(targets.stream().map(target -> {
				InformEventTargetDTO targetDTO = new InformEventTargetDTO();
				targetDTO.setId(target.getId());
				targetDTO.setToType(target.getToType());
				targetDTO.setTargetId(target.getTargetId());
				return targetDTO;

			}).collect(Collectors.toList()));
		}
		return targetsDTO;
	}

}
