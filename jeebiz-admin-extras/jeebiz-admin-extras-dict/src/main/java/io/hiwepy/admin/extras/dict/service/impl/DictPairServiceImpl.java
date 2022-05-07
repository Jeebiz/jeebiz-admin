/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.dict.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import hitool.core.collections.CollectionUtils;
import io.hiwepy.admin.extras.dict.dao.DictPairMapper;
import io.hiwepy.admin.extras.dict.dao.entities.DictPairEntity;
import io.hiwepy.admin.extras.dict.service.IDictPairService;
import io.hiwepy.admin.extras.dict.setup.event.DictPairDeletedEvent;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class DictPairServiceImpl extends BaseServiceImpl<DictPairMapper, DictPairEntity> implements IDictPairService {

	@Autowired
	private RedisOperationTemplate redisOperation;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(DictPairEntity model) {
		// 清理Redis缓存
		String dictRedisKey = BizRedisKey.APP_DICT.getKey(model.getGkey());
		getRedisOperation().del(dictRedisKey);
		return super.save(model);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<?> list) {
		List<String> groups = getBaseMapper().getGroupList(list);
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new DictPairDeletedEvent(this, gkey));
		}
		return super.removeByIds(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String id, String status) {
		List<String> groups = getBaseMapper().getGroupList(Arrays.asList(id));
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new DictPairDeletedEvent(this, gkey));
		}
		return getBaseMapper().setStatus(id, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBatchById(Collection<DictPairEntity> list) {
		List<String> groups = getBaseMapper().getGroupList(list.stream().map(m -> { return m.getId();}).collect(Collectors.toList()));
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new DictPairDeletedEvent(this, gkey));
		}
		return super.updateBatchById(list);
	}

	@Override
	public Map<String, List<DictPairEntity>> getGroupPairValues(String[] gkeys) {
		List<DictPairEntity> keyValueList = getBaseMapper().getDictPairList(Arrays.asList(gkeys));
		if(CollectionUtils.isEmpty(keyValueList)) {
			return Maps.newHashMap();
		}

		Map<String, List<DictPairEntity>> kvMaps = keyValueList.stream()
				.collect(Collectors.groupingBy(DictPairEntity::getGkey, Collectors.toList()));

		return kvMaps;
	}

	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getBaseMapper().getPairValues(gkey);
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}
}
