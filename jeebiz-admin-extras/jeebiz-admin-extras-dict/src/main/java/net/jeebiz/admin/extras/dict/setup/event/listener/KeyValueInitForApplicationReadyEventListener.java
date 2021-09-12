package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import net.jeebiz.admin.api.BizRedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.dict.dao.IKeyGroupDao;
import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Component
public class KeyValueInitForApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private IKeyGroupDao keyGroupDao;
	@Autowired
	private IKeyValueDao keyValueDao;
	@Autowired
	private RedisOperationTemplate redisOperation;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		List<PairModel> groups = getKeyGroupDao().getPairList();
		for (PairModel group : groups) {
			String dictRedisKey = BizRedisKey.APP_DICT.getKey(group.getKey());
			if(!getRedisOperation().hasKey(dictRedisKey)) {
				List<PairModel> retList = getKeyValueDao().getPairValues(group.getKey());
				//getRedisOperationTemplate().set(Constants.KEY_PREFIX + group.getKey(), retList);
				getRedisOperation().lLeftPush(dictRedisKey, retList);
			}
		}
		
	}

	public IKeyGroupDao getKeyGroupDao() {
		return keyGroupDao;
	}

	public IKeyValueDao getKeyValueDao() {
		return keyValueDao;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}
	
}
