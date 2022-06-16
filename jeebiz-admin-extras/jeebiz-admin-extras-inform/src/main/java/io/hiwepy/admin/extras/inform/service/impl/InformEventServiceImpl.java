/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.service.impl;

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
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InformEventServiceImpl extends BaseServiceImpl<InformEventMapper, InformEventEntity>
		implements IInformEventService {

	@Autowired
	private IInformTargetService informTargetService;

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
	public boolean setTargets(InformEventTargetsDTO targetsDto) {

		// 1、如果提交的列表没有数据，表示全部删除
		if(CollectionUtils.isEmpty(targetsDto.getTargets())){
			informTargetService.remove(new LambdaQueryWrapper<InformTargetEntity>()
					.eq(InformTargetEntity::getEventId, targetsDto.getEventId()));
		}
		// 2、否则就是新增或者更新操作
		else {

			// 2.1、查询角色已经设置的授权机构
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
				});
			} else {
				// 删除移除的
				List<String> deleteIds = targets.stream().filter(target -> targetsDto.getTargets().stream().noneMatch(targetDto -> {
					return target.getToType().equals(targetDto.getToType()) && targetDto.getTargetId().equals( target.getTargetId());
				})).map(InformTargetEntity::getId).collect(Collectors.toList());
				informTargetService.removeBatchByIds(deleteIds);
				// 保存或者更新新的设置
				targetsDto.getTargets().stream().map(targetDto -> {
					InformTargetEntity entity = new InformTargetEntity();
					entity.setId(targetDto.getId());
					entity.setEventId(targetsDto.getEventId());
					entity.setToType(targetDto.getToType());
					entity.setTargetId(targetDto.getTargetId());
					return entity;
				}).forEach(entity -> {
					informTargetService.saveOrUpdate(entity);
				});

			}
		}

		// 设置消息事件的通知对象类型
		InformEventEntity entity = new InformEventEntity();
		ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
		entity.setId(targetsDto.getEventId());
		entity.setModifyer(principal.getUserid());
		entity.setTarget(InformTarget.ALL);
		getBaseMapper().updateById(entity);

		return true;
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
