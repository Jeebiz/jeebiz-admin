package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.admin.extras.dict.setup.event.KeyValueDeletedEvent;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Component
public class KeyValueDeletedEventListener implements ApplicationListener<KeyValueDeletedEvent> {

	@Autowired
	private IKeyValueDao keyValueDao;
	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	
	@Override
	public void onApplicationEvent(KeyValueDeletedEvent event) {
		if(!getRedisOperationTemplate().hasKey(Constants.KEY_PREFIX + event.getKey())) {
			getRedisOperationTemplate().del(event.getKey());
		}
		try {
			List<PairModel> retList = getKeyValueDao().getPairValues(event.getKey());
			getRedisOperationTemplate().lLeftPush(Constants.KEY_PREFIX + event.getKey(), retList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IKeyValueDao getKeyValueDao() {
		return keyValueDao;
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}
	
}
