package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.api.BizRedisKey;
import net.jeebiz.admin.extras.dict.dao.DictGroupMapper;
import net.jeebiz.admin.extras.dict.dao.DictPairMapper;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Component
public class DictPairInitForApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private DictGroupMapper keyGroupMapper;
	@Autowired
	private DictPairMapper keyValueMapper;
	@Autowired
	private RedisOperationTemplate redisOperation;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		List<PairModel> groups = getKeyGroupMapper().getPairList();
		for (PairModel group : groups) {
			String dictRedisKey = BizRedisKey.APP_DICT.getKey(group.getKey());
			if(!getRedisOperation().hasKey(dictRedisKey)) {
				List<PairModel> retList = getKeyValueMapper().getPairValues(group.getKey());
				//getRedisOperationTemplate().set(Constants.KEY_PREFIX + group.getKey(), retList);
				getRedisOperation().lLeftPush(dictRedisKey, retList);
			}
		}
		
	}

	public DictGroupMapper getKeyGroupMapper() {
		return keyGroupMapper;
	}

	public DictPairMapper getKeyValueMapper() {
		return keyValueMapper;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}
	
}
