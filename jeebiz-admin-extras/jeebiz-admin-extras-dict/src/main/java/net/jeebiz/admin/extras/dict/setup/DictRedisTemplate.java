package net.jeebiz.admin.extras.dict.setup;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import net.jeebiz.admin.extras.dict.dao.DictMapper;
import net.jeebiz.boot.api.dao.entities.PairModel;

public class DictRedisTemplate {
	
	private static final String KEY_PREFIX = "Dict:";
	
	private final RedisTemplate<String, Object> redisTemplate;
	private final DictMapper dictMapper;
	
	public DictRedisTemplate(RedisTemplate<String, Object> redisTemplate, 
			DictMapper dictMapper) {
		super();
		this.redisTemplate = redisTemplate;
		this.dictMapper = dictMapper;
	}
	
	@SuppressWarnings("unchecked")
	public List<PairModel> get(String key){
		if(getRedisTemplate().hasKey(KEY_PREFIX + key)) {
			List<Object> list =  getRedisTemplate().opsForList().range(KEY_PREFIX + key,0,-1);
			return (List<PairModel>) list.get(0);
		} else {
			List<PairModel> retList = getDictMapper().getPairValues(key);
			getRedisTemplate().opsForList().leftPush(KEY_PREFIX + key, retList);
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
	
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public DictMapper getDictMapper() {
		return dictMapper;
	}

}
