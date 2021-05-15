/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.dao.entities.KeyValueModel;
import net.jeebiz.admin.extras.dict.service.IKeyValueService;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.admin.extras.dict.setup.event.KeyValueDeletedEvent;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseMapperServiceImpl;
import net.jeebiz.boot.api.utils.CollectionUtils;

@Service
public class KeyValueServiceImpl extends BaseMapperServiceImpl<KeyValueModel, IKeyValueDao> implements IKeyValueService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(KeyValueModel model) {
		// 清理Redis缓存
		if(getRedisTemplate().hasKey(Constants.KEY_PREFIX + model.getGkey())) {
			getRedisTemplate().delete(Constants.KEY_PREFIX + model.getGkey());
		}
		return super.save(model);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeByIds(Collection<? extends Serializable> list) {
		List<String> groups = getBaseMapper().getGroupList(list);
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new KeyValueDeletedEvent(this, gkey));
		}
		return super.removeByIds(list);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String id, String status) {
		List<String> groups = getBaseMapper().getGroupList(Arrays.asList(id));
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new KeyValueDeletedEvent(this, gkey));
		}
		return getBaseMapper().setStatus(id, status);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBatchById(Collection<KeyValueModel> list) {
		List<String> groups = getBaseMapper().getGroupList(list.stream().map(m -> { return m.getId();}).collect(Collectors.toList()));
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new KeyValueDeletedEvent(this, gkey));
		}
		return super.updateBatchById(list);
	}

	@Override
	public Map<String, List<KeyValueModel>> getGroupPairValues(String[] gkeys) {
		List<KeyValueModel> keyValueList = getBaseMapper().getKeyValueList(Arrays.asList(gkeys));
		if(CollectionUtils.isEmpty(keyValueList)) {
			return Maps.newHashMap();
		}
		
		Map<String, List<KeyValueModel>> kvMaps = keyValueList.stream()
				.collect(Collectors.groupingBy(KeyValueModel::getGkey, Collectors.toList()));

		return kvMaps;
	}
	
	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getBaseMapper().getPairValues(gkey);
	}
	
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

}
