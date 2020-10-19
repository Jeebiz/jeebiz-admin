/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.service.impl;

import java.util.Arrays;
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
import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.boot.api.utils.CollectionUtils;

@Service
public class KeyValueServiceImpl extends BaseServiceImpl<KeyValueModel, IKeyValueDao> implements IKeyValueService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(KeyValueModel model) {
		// 清理Redis缓存
		if(getRedisTemplate().hasKey(Constants.KEY_PREFIX + model.getGkey())) {
			getRedisTemplate().delete(Constants.KEY_PREFIX + model.getGkey());
		}
		return super.insert(model);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(List<?> list) {
		List<String> groups = getDao().getGroupList(list);
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new KeyValueDeletedEvent(this, gkey));
		}
		return super.batchDelete(list);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setStatus(String id, String status) {
		List<String> groups = getDao().getGroupList(Arrays.asList(id));
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new KeyValueDeletedEvent(this, gkey));
		}
		return super.setStatus(id, status);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchUpdate(List<KeyValueModel> list) {
		List<String> groups = getDao().getGroupList(list.stream().map(m -> { return m.getId();}).collect(Collectors.toList()));
		for (String gkey : groups) {
			getEventPublisher().publishEvent(new KeyValueDeletedEvent(this, gkey));
		}
		return super.batchUpdate(list);
	}

	@Override
	public Map<String, List<KeyValueModel>> getGroupPairValues(String[] gkeys) {
		List<KeyValueModel> keyValueList = getDao().getKeyValueList(Arrays.asList(gkeys));
		if(CollectionUtils.isEmpty(keyValueList)) {
			return Maps.newHashMap();
		}
		
		Map<String, List<KeyValueModel>> kvMaps = keyValueList.stream()
				.collect(Collectors.groupingBy(KeyValueModel::getGkey, Collectors.toList()));

		return kvMaps;
	}
	
	@Override
	public List<PairModel> getPairValues(String gkey) {
		return getDao().getPairValues(gkey);
	}
	
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

}
