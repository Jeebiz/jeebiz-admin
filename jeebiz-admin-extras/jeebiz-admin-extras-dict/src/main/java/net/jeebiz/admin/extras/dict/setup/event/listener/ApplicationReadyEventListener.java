package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.dict.dao.IKeyGroupDao;
import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private IKeyGroupDao keyGroupDao;
	@Autowired
	private IKeyValueDao keyValueDao;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		List<PairModel> groups = getKeyGroupDao().getPairList();
		for (PairModel group : groups) {
			if(!getRedisTemplate().hasKey(Constants.KEY_PREFIX + group.getKey())) {
				List<PairModel> retList = getKeyValueDao().getPairValues(group.getKey());
				getRedisTemplate().opsForList().leftPush(Constants.KEY_PREFIX + group.getKey(), retList);
			}
		}
		
	}

	public IKeyGroupDao getKeyGroupDao() {
		return keyGroupDao;
	}

	public IKeyValueDao getKeyValueDao() {
		return keyValueDao;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	
}
