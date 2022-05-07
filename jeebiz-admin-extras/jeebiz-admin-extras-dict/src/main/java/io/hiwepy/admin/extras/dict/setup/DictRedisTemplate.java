package io.hiwepy.admin.extras.dict.setup;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisOperationTemplate;

import io.hiwepy.admin.extras.dict.dao.DictMapper;
import io.hiwepy.boot.api.dao.entities.PairModel;

public class DictRedisTemplate {
	
	private static final String KEY_PREFIX = "Dict:";
	
	private final RedisOperationTemplate redisOperation;
	private final DictMapper dictMapper;
	
	public DictRedisTemplate(RedisOperationTemplate redisOperation, 
			DictMapper dictMapper) {
		super();
		this.redisOperation = redisOperation;
		this.dictMapper = dictMapper;
	}
	
	@SuppressWarnings("unchecked")
	public List<PairModel> get(String key){
		if(redisOperation.hasKey(KEY_PREFIX + key)) {
			List<Object> list = redisOperation.lRange(KEY_PREFIX + key, 0, -1);
			return (List<PairModel>) list.get(0);
		} else {
			List<PairModel> retList = getDictMapper().getPairValues(key);
			redisOperation.lLeftPush(KEY_PREFIX + key, retList);
			return retList;
		}
	}
	
	public String getValue(String key, String code){
		List<PairModel> retList = this.get(key);
		if(Objects.nonNull(retList)) {
			Optional<PairModel> optional = retList.stream().filter(item -> StringUtils.equals(item.getKey(), code)).findFirst();
			return optional.isPresent() ? optional.get().getValue() : null;
		}
		return null;
	}

	public DictMapper getDictMapper() {
		return dictMapper;
	}

}
