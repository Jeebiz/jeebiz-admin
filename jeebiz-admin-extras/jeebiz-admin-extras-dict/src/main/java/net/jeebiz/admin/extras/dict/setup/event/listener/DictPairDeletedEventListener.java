package net.jeebiz.admin.extras.dict.setup.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.api.BizRedisKey;
import net.jeebiz.admin.extras.dict.dao.DictPairMapper;
import net.jeebiz.admin.extras.dict.setup.event.DictPairDeletedEvent;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Component
public class DictPairDeletedEventListener implements ApplicationListener<DictPairDeletedEvent> {

	@Autowired
	private DictPairMapper keyValueMapper;
	@Autowired
	private RedisOperationTemplate redisOperation;
	
	@Override
	public void onApplicationEvent(DictPairDeletedEvent event) {

		// 清理Redis缓存
		String dictRedisKey = BizRedisKey.APP_DICT.getKey(event.getKey());
		getRedisOperation().del(dictRedisKey);
		try {
			List<PairModel> retList = getKeyValueMapper().getPairValues(event.getKey());
			getRedisOperation().lLeftPush(dictRedisKey, retList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DictPairMapper getKeyValueMapper() {
		return keyValueMapper;
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}
	
}
