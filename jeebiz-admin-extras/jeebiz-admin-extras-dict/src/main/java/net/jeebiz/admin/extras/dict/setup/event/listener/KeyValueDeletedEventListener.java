package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.admin.extras.dict.setup.event.KeyValueDeletedEvent;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Component
public class KeyValueDeletedEventListener implements ApplicationListener<KeyValueDeletedEvent> {

	@Autowired
	private IKeyValueDao keyValueDao;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void onApplicationEvent(KeyValueDeletedEvent event) {
		if(!getRedisTemplate().hasKey(Constants.KEY_PREFIX + event.getKey())) {
			getRedisTemplate().delete(event.getKey());
		}
		List<PairModel> retList = getKeyValueDao().getPairValues(event.getKey());
		getRedisTemplate().opsForList().leftPushAll(Constants.KEY_PREFIX + event.getKey(), retList);
	}

	public IKeyValueDao getKeyValueDao() {
		return keyValueDao;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	
}
