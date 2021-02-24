package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.extras.dict.dao.IKeyGroupDao;
import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Component
public class KeyValueInitForApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private IKeyGroupDao keyGroupDao;
	@Autowired
	private IKeyValueDao keyValueDao;
	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		List<PairModel> groups = getKeyGroupDao().getPairList();
		for (PairModel group : groups) {
			if(!getRedisOperationTemplate().hasKey(Constants.KEY_PREFIX + group.getKey())) {
				try {
					List<PairModel> retList = getKeyValueDao().getPairValues(group.getKey());
					//getRedisOperationTemplate().set(Constants.KEY_PREFIX + group.getKey(), retList);
					getRedisOperationTemplate().lLeftPush(Constants.KEY_PREFIX + group.getKey(), retList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public IKeyGroupDao getKeyGroupDao() {
		return keyGroupDao;
	}

	public IKeyValueDao getKeyValueDao() {
		return keyValueDao;
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}
	
}
