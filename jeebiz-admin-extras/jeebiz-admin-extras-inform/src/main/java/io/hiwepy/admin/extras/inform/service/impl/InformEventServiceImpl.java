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

		// 2、如果设置的是部分通知，则进行通知对象处理
		if(InformTarget.SPECIFIC.equals(targetsDto.getTarget())){

			// 2.1、如果提交的列表没有数据，表示全部删除
			if(CollectionUtils.isEmpty(targetsDto.getTargets())){
				this.removeTargetByEventId(targetsDto.getEventId());
				return true;
			}

			// 2.2、查询角色已经设置的授权机构
			List<InformTargetEntity> targets = informTargetService.list(new LambdaQueryWrapper<InformTargetEntity>()
					.select(InformTargetEntity::getId, InformTargetEntity::getToType, InformTargetEntity::getTargetId)
					.eq(InformTargetEntity::getEventId, targetsDto.getEventId())
					.eq(InformTargetEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
			// 2.2、如果历史没数据，则表示全部为新增
			if(CollectionUtils.isEmpty(targets)){
				targetsDto.getTargets().stream().forEach(targetDto -> {
					InformTargetEntity entity = new InformTargetEntity();
					entity.setEventId(targetsDto.getEventId());
					entity.setToType(targetDto.getToType());
					entity.setTargetId(targetDto.getTargetId());
					informTargetService.save(entity);

					String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetDto.getToType(), eventEntity.getId());
					redisOperation.zAdd(targetRedisKey, targetDto.getTargetId(), System.currentTimeMillis());

				});
			} else {
				// 删除移除的
				List<String> deleteIds = targets.stream().filter(target -> targetsDto.getTargets().stream().noneMatch(targetDto -> {
					return target.getToType().equals(targetDto.getToType()) && targetDto.getTargetId().equals( target.getTargetId());
				})).map(InformTargetEntity::getId).collect(Collectors.toList());
				informTargetService.removeBatchByIds(deleteIds);
				// 保存或者更新新的设置
				List<InformTargetEntity> saveOrUpdates = targetsDto.getTargets().stream().map(targetDto -> {
					InformTargetEntity entity = new InformTargetEntity();
					entity.setId(targetDto.getId());
					entity.setEventId(targetsDto.getEventId());
					entity.setToType(targetDto.getToType());
					entity.setTargetId(targetDto.getTargetId());
					return entity;
				}).collect(Collectors.toList());
				saveOrUpdates.forEach(entity -> {
					// 保存或更新数据库
					informTargetService.saveOrUpdate(entity);
				});

				// 同步更新缓存
				targets.stream().filter(targetEntity -> targetsDto.getTargets().stream().noneMatch(targetDto -> {
					return targetEntity.getToType().equals(targetDto.getToType()) && targetDto.getTargetId().equals( targetEntity.getTargetId());
				})).forEach(targetEntity -> {
					String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetEntity.getToType(), eventEntity.getId());
					redisOperation.zRem(targetRedisKey, targetEntity.getTargetId());
				});
				saveOrUpdates.forEach(targetEntity -> {
					// 保存或更新数据库
					String targetRedisKey = BizRedisKey.INFORM_TARGET.getKey(targetEntity.getToType(), eventEntity.getId());
					redisOperation.zAdd(targetRedisKey, targetEntity.getTargetId(), System.currentTimeMillis());
				});
			}

			return true;

		} else {
			// 其他情况，如果有原始数据则进行清理
			this.removeTargetByEventId(targetsDto.getEventId());
			return true;
		}
	}

	protected void removeTargetByEventId(String eventId){
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
