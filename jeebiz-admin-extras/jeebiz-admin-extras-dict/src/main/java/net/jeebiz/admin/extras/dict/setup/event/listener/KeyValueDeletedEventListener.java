package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import net.jeebiz.admin.api.BizRedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.dict.dao.IKeyValueDao;
import net.jeebiz.admin.extras.dict.setup.Constants;
import net.jeebiz.admin.extras.dict.setup.event.KeyValueDeletedEvent;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Component
public class KeyValueDeletedEventListener implements ApplicationListener<KeyValueDeletedEvent> {

	@Autowired
	private IKeyValueDao keyValueDao;
	@Autowired
	private RedisOperationTemplate redisOperation;
	
	@Override
	public void onApplicationEvent(KeyValueDeletedEvent event) {

		// 清理Redis缓存
		String dictRedisKey = BizRedisKey.APP_DICT.getKey(event.getKey());
		getRedisOperation().del(dictRedisKey);
		try {
			List<PairModel> retList = getKeyValueDao().getPairValues(event.getKey());
			getRedisOperation().lLeftPush(dictRedisKey, retList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IKeyValueDao getKeyValueDao() {
		return keyValueDao;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}
	
}
