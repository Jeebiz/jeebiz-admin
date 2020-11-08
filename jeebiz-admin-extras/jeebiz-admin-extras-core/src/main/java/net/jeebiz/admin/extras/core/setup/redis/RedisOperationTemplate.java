package net.jeebiz.admin.extras.core.setup.redis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.boot.api.exception.BizRuntimeException;

@SuppressWarnings({"unchecked","rawtypes"})
@Slf4j
public class RedisOperationTemplate {

	private final RedisTemplate<String, Object> redisTemplate;
	
	public RedisOperationTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	// =============================common============================

	/**
	 * 指定缓存失效时间
	 * @param key  键
	 * @param time 时间(秒)
	 * @return
	 */
	public Boolean expire(String key, long seconds) {
		if (seconds <= 0) {
			return Boolean.FALSE;
		}
		try {
			return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Boolean.FALSE;
		}
	}

	/**
	 * 指定缓存失效时间
	 * @param key  键
	 * @param timeout 时间
	 * @return
	 */
	public Boolean expire(String key, Duration timeout) {
		try {
			return redisTemplate.expire(key, timeout);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public Boolean expireAt(String key, Date date) {
		try {
			return redisTemplate.expireAt(key, date);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public Boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	// 模糊匹配缓存中的key
	public Set<String> getKey(String pattern) {
		if (Objects.isNull(pattern)) {
			return new HashSet<>();
		}
		return redisTemplate.keys(pattern);
	}

	// 模糊匹配缓存中的key
	public Set<String> getVagueKey(String pattern) {
		return redisTemplate.keys("*" + pattern + "*");
	}

	public Set<String> getValueKeyByPrefix(String prefixPattern) {
		return redisTemplate.keys(prefixPattern + "*");
	}
	
	/**
	 * 删除缓存
	 * @param keys 可以传一个值 或多个
	 */
	public void delete(String... keys) {
		try {
			if (keys != null && keys.length > 0) {
				if (keys.length == 1) {
					redisTemplate.delete(keys[0]);
				} else {
					redisTemplate.delete(CollectionUtils.arrayToList(keys));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public <K> byte[] rawKey(K key) {

		Assert.notNull(key, "non null key required");

		if (keySerializer() == null && key instanceof byte[]) {
			return (byte[]) key;
		}

		return keySerializer().serialize(key);
	}
	
	public byte[] rawValue(Object value) {
		if (valueSerializer() == null && value instanceof byte[]) {
			return (byte[]) value;
		}
		return valueSerializer().serialize(value);
	}
	
	RedisSerializer keySerializer() {
		return redisTemplate.getKeySerializer();
	}

	RedisSerializer valueSerializer() {
		return redisTemplate.getValueSerializer();
	}

	RedisSerializer hashKeySerializer() {
		return redisTemplate.getHashKeySerializer();
	}

	RedisSerializer hashValueSerializer() {
		return redisTemplate.getHashValueSerializer();
	}

	RedisSerializer stringSerializer() {
		return redisTemplate.getStringSerializer();
	}

	// ============================String=============================

	/**
	 * 普通缓存放入
	 *
	 * @param key   键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒) time要>=0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, long seconds) {
		try {
			if (seconds > 0) {
				redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
				return true;
			} else {
				return set(key, value);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key   键
	 * @param value 值
	 * @param duration  时间
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, Duration duration) {
		if (Objects.isNull(duration) || duration.isNegative()) {
			return false;
		}
		try {
			redisTemplate.opsForValue().set(key, value, duration);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public boolean setNX(String key, String value) {
		try {
			//return redisTemplate.opsForValue().setIfAbsent(key, value);
			return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
				byte[] serKey = redisTemplate.getStringSerializer().serialize(key);
				byte[] serValue = redisTemplate.getStringSerializer().serialize(value);
				return redisConnection.setNX(serKey, serValue);
			});
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean setEx(String key, String value, long seconds) {
		try {
			//redisTemplate.opsForValue().set(key, value, Duration.ofMillis(seconds));
			//return true;
			return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
				return redisConnection.setEx(rawKey(key), seconds, rawValue(value));
			});
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	 
	/**
	 * 普通缓存获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		try {
			return !StringUtils.hasText(key) ? null : redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据key表达式获取缓存
	 * @param pattern 键表达式
	 * @return 值
	 */
	public List<Object> multiGet(String pattern) {
		try {
			if (!StringUtils.hasText(pattern)) {
				return Lists.newArrayList();
			}
			Set<String> keys = redisTemplate.keys(pattern);
	        return redisTemplate.opsForValue().multiGet(keys);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Lists.newArrayList();
		}
	}
	
	/**
	 * 批量获取缓存值
	 * @param keys 键集合
	 * @return 值
	 */
	public List<Object> multiGet(Collection keys) {
		try {
			return CollectionUtils.isEmpty(keys) ? Lists.newArrayList() : redisTemplate.opsForValue().multiGet(keys);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Lists.newArrayList();
		}
	}
	
	/**
	 * 递增
	 * @param key   键
	 * @param delta 要增加几(>=0)
	 * @return
	 */
	public Long incr(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 要增加几(>=0)
	 * @param seconds 过期时长（秒）
	 * @return
	 */
	public Long incr(String key, long delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Long increment = redisTemplate.opsForValue().increment(key, delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}
	
	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 要增加几(>=0)
	 * @return
	 */
	public Double incr(String key, double delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}
	
	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 要增加几(>=0)
	 * @param seconds 过期时长（秒）
	 * @return
	 */
	public Double incr(String key, double delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Double increment = redisTemplate.opsForValue().increment(key, delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}

	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 要减少几(>=0)
	 * @return
	 */
	public Long decr(String key, long delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 要减少几(>=0)
	 * @param seconds 过期时长（秒）
	 * @return
	 */
	public Long decr(String key, long delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		Long increment = redisTemplate.opsForValue().increment(key, -delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}
	
	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 要减少几(>=0)
	 * @return
	 */
	public Double decr(String key, double delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}
	
	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 要减少几(>=0)
	 * @param seconds 过期时长（秒）
	 * @return
	 */
	public Double decr(String key, double delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		Double increment = redisTemplate.opsForValue().increment(key, -delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}

	// ================================Map=================================

	/**
	 * HashGet
	 *
	 * @param key  键 不能为null
	 * @param hashKey 项 不能为null
	 * @return 值
	 */
	public Object hget(String key, String hashKey) {
		try {
			return redisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取hashKey对应的指定键值
	 * @param key 键
	 * @param hashKeys 要筛选项
	 * @return
	 */
    public List<Object> hmultiGet(String key, Collection hashKeys) {
    	try {
			return redisTemplate.opsForHash().multiGet(key, hashKeys);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Lists.newArrayList();
		}
	}

	/**
	 * 获取hashKey对应的所有键值
	 *
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object, Object> hmget(String key) {
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Maps.newHashMap();
		}
	}

	/**
	 * 获取hashKey对应的所有键值
	 *
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<String, Object> hmget2(String key) {
		try {
			HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
			return opsForHash.entries(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return Maps.newHashMap();
		}
	}

	/**
	 * HashSet
	 *
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 *
	 * @param key  键
	 * @param map  对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hmset(String key, Map<String, Object> map, long seconds) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (seconds > 0) {
				expire(key, seconds);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public boolean hmset(String key, Map<String, Object> map, Duration duration) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if(!duration.isNegative()) {
				expire(key, duration);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean hset(String key, String item, Object value, long seconds) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (seconds > 0) {
				expire(key, seconds);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public boolean hset(String key, String item, Object value, Duration duration) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if(!duration.isNegative()) {
				expire(key, duration);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * hash的大小
	 * 
	 * @param key
	 * @return
	 */
	public Long hsize(String key) {
		try {
			return redisTemplate.opsForHash().size(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 删除hash表中的值
	 *
	 * @param key  键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**
	 * 判断hash表中是否有该项的值
	 *
	 * @param key  键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}
	
	public Set<Object> hKeys(String key) {
		return redisTemplate.opsForHash().keys(key);
	}
	
	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta  要增加几(>=0)
	 * @return
	 */
	public Long hincr(String key, String item, int delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		return redisTemplate.opsForHash().increment(key, item, delta);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta  要增加几(>=0)
	 * @param seconds 过期时长（秒）
	 * @return
	 */
	public Long hincr(String key, String item, int delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Long increment = redisTemplate.opsForHash().increment(key, item, delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}
	
	public Long hincr(String key, String item, int delta, Duration duration) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Long increment = redisTemplate.opsForHash().increment(key, item, delta);
		if(!duration.isNegative()) {
			expire(key, duration);
		}
		return increment;
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta  要增加几(>=0)
	 * @return
	 */
	public Long hincr(String key, String item, long delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		return redisTemplate.opsForHash().increment(key, item, delta);
	}
	
	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta  要增加几(>=0)
	 * @param seconds 过期时长（秒）
	 * @return
	 */
	public Long hincr(String key, String item, long delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Long increment = redisTemplate.opsForHash().increment(key, item, delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}

	public Long hincr(String key, String item, long delta, Duration duration) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Long increment = redisTemplate.opsForHash().increment(key, item, delta);
		if(!duration.isNegative()) {
			expire(key, duration);
		}
		return increment;
	}
	
	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta   要增加几(>=0)
	 * @return
	 */
	public Double hincr(String key, String item, double delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		return redisTemplate.opsForHash().increment(key, item, delta);
	}
	
	public Double hincr(String key, String item, double delta, long seconds) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Double increment = redisTemplate.opsForHash().increment(key, item, delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return increment;
	}
	
	public Double hincr(String key, String item, double delta, Duration duration) {
		if (delta < 0) {
			throw new BizRuntimeException("递增因子必须>=0");
		}
		Double increment = redisTemplate.opsForHash().increment(key, item, delta);
		if(!duration.isNegative()) {
			expire(key, duration);
		}
		return increment;
	}

	/**
	 * hash递减
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta   要减少记(小于0)
	 * @return
	 */
	public Long hdecr(String key, String item, int delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}
	
	/**
	 * hash递减
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta   要减少记(>=0)
	 * @return
	 */
	public Long hdecr(String key, String item, long delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}

	/**
	 * hash递减
	 *
	 * @param key  键
	 * @param item 项
	 * @param delta 要减少记(>=0)
	 * @return
	 */
	public Double hdecr(String key, String item, double delta) {
		if (delta < 0) {
			throw new BizRuntimeException("递减因子必须>=0");
		}
		return redisTemplate.opsForHash().increment(key, item, -delta);
	}
	
	// ============================set=============================

	/**
	 * 根据key获取Set中的所有值
	 *
	 * @param key 键
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 根据key获取Set中的所有值
	 *
	 * @param key 键
	 * @return
	 */
	public Set<Long> sGetLong(String key) {
		try {
			Set<Object> members = redisTemplate.opsForSet().members(key);
			return members.stream().map(object -> Long.valueOf(object.toString())).collect(Collectors.toSet());
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 *
	 * @param key   键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public Long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 将set数据放入缓存
	 *
	 * @param key    键
	 * @param seconds   过期时长(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public Long sSetAndTime(String key, long seconds, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (seconds > 0) {
				expire(key, seconds);
			}
			return count;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 获取set缓存的长度
	 *
	 * @param key 键
	 * @return
	 */
	public Long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 移除值为value的
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public Long sRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 获取两个key的不同value
	 * 
	 * @param key1 键
	 * @param key2 键
	 * @return 返回key1中和key2的不同数据
	 */
	public Set<Object> sDiff(String key1, String key2) {
		try {
			return redisTemplate.opsForSet().difference(key1, key2);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取两个key的不同数据，放到key3中
	 * 
	 * @param key1 键
	 * @param key2 键
	 * @param key3 键
	 * @return 返回成功数据
	 */
	public Boolean sDifferenceAndStore(String key1, String key2, String key3) {
		try {
			redisTemplate.opsForSet().differenceAndStore(key1, key2, key3);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 获取两个key的所有数据，放到key3中
	 * 
	 * @param key1 键
	 * @param key2 键
	 * @param key3 键
	 * @return 返回成功数据
	 */
	public Boolean sUnionAndStore(String key1, String key2, String key3) {
		try {
			redisTemplate.opsForSet().unionAndStore(key1, key2, key3);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 随机获取指定数量的元素,同一个元素可能会选中两次
	 * 
	 * @param key
	 * @param count
	 * @return
	 */
	public List<Object> sRandomSet(String key, long count) {
		return redisTemplate.opsForSet().randomMembers(key, count);
	}

	/**
	 * 随机获取指定数量的元素,去重(同一个元素只能选择一次)
	 * 
	 * @param key
	 * @param count
	 * @return
	 */
	public Set<Object> sRandomSetDistinct(String key, long count) {
		return redisTemplate.opsForSet().distinctRandomMembers(key, count);
	}

	// ===============================list=================================

	/**
	 * 获取list缓存的内容
	 *
	 * @param key   键
	 * @param start 开始
	 * @param end   结束 0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取list缓存的内容
	 *
	 * @param key   键
	 * @param start 开始
	 * @param end   结束 0 到 -1代表所有值
	 * @return
	 */
	public List<Long> lGetLong(String key, long start, long end) {
		try {
			List<Object> range = redisTemplate.opsForList().range(key, start, end);
			List<Long> result = range.stream().map(object -> Long.valueOf(object.toString()))
					.collect(Collectors.toList());
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 *
	 * @param key 键
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 *
	 * @param key   键
	 * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public boolean lRightPush(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒)
	 * @return
	 */
	public Long lRightPush(String key, Object value, long seconds) {
		try {
			Long rt = redisTemplate.opsForList().rightPush(key, value);
			if (seconds > 0) {
				expire(key, seconds);
			}
			return rt;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param values 值
	 * @return
	 */
	public Long lRightPush(String key, List<Object> values) {
		try {
			return redisTemplate.opsForList().rightPushAll(key, values);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}
	
	/**
	 * 将元素放到list左边
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public Long lLeftPush(String key, Object value) {
		try {
			return redisTemplate.opsForList().leftPush(key, value);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param values 值
	 * @param seconds  时间(秒)
	 * @return
	 */
	public Long lLeftPush(String key, List<Object> values, long seconds) {
		try {
			Long rt = redisTemplate.opsForList().rightPushAll(key, values);
			if (seconds > 0) {
				expire(key, seconds);
			}
			return rt;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}
	
	public Long lLeftPush(String key, List<Object> values, Duration duration) {
		try {
			Long rt = redisTemplate.opsForList().rightPushAll(key, values);
			if(!duration.isNegative()) {
				expire(key, duration);
			}
			return rt;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0L;
		}
	}
	
	public Object lLeftPop(String key) {
		try {
			return redisTemplate.opsForList().leftPop(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 *
	 * @param key   键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 *
	 * @param key   键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
	}

	public boolean lRemoveAll(String key, int start, int end) {
		try {
			redisTemplate.opsForList().trim(key, start, end);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	// ===============================ZSet=================================
	
	/**
	 * Zset添加元素
	 * 
	 * @param key
	 * @param value
	 * @param score
	 */
	public Double zincr(String key, String value, double delta) {
		return redisTemplate.opsForZSet().incrementScore(key, value, delta);
	}

	public Double zincr(String key, String value, double delta, long seconds) {
		Double result = redisTemplate.opsForZSet().incrementScore(key, value, delta);
		if (seconds > 0) {
			expire(key, seconds);
		}
		return result;
	}

	public Boolean updateZsetScore(String key, String value, double score) {
		return redisTemplate.opsForZSet().add(key, value, score);
	}

	public Long addZsetScore(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
		return redisTemplate.opsForZSet().add(key, tuples);
	}

	/**
	 * 获取指定key的scores正序，指定start-end位置的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, Integer start, Integer end) {
		return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
	}

	/**
	 * 通过分数返回有序集合指定区间内的成员个数
	 * 
	 * @param key
	 * @param min
	 * @param max
	 */
	public Long zsetCountByscores(String key, double min, double max) {
		return redisTemplate.opsForZSet().count(key, min, max);
	}

	/**
	 * 移除zset中的元素
	 * 
	 * @param key
	 * @param value
	 */
	public Long removeZsetValue(String key, Object... value) {
		return redisTemplate.opsForZSet().remove(key, value);
	}

	/**
	 * 移除分数区间内的元素
	 * 
	 * @param key
	 * @param min
	 * @param max
	 */
	public void removeZetRangeByScore(String key, double min, double max) {
		redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
	}

	public Double getZscore(String key, Object value) {
		return redisTemplate.opsForZSet().score(key, value);
	}

	public Long reverseRank(String key, Object value) {
		return redisTemplate.opsForZSet().reverseRank(key, value);
	}

	public Long getZcard(String key) {
		return redisTemplate.opsForZSet().zCard(key);
	}

	/**
	 * @Author: cmm
	 * @Description:
	 * @Date: 2020/6/7
	 * @param key   :
	 * @param start :
	 * @param end   :0 到-1表示查全部
	 * @return {@link Set< Object>}
	 */
	public Set<Object> getZrevrange(String key, int start, int end) {
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}

	/**
	 * @Author: cmm
	 * @Description:
	 * @Date: 2020/6/7
	 * @param key   :
	 * @param start :
	 * @param end   :0 到-1表示查全部
	 * @return {@link Set< Long>}
	 */
	public Set<Long> getLongZrevrange(String key, int start, int end) {
		Set<Object> objects = redisTemplate.opsForZSet().reverseRange(key, start, end);
		Set<Long> collect = objects.stream().map(object -> Long.valueOf(object.toString()))
				.collect(Collectors.toCollection(LinkedHashSet::new));
		return collect;
	}

	public Set<Object> getZetRangeByScore(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}

	/**
	 * 在min到max范围内倒序获取zset及对应的score
	 */
	public Set<ZSetOperations.TypedTuple<Object>> getZetRangeByScoreWithScores(String key, double min, double max) {
		return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
	}

	/**
	 * 批量获取hash 某个值
	 * 
	 * @param redisKey
	 * @param redisField
	 */
	public List<Object> batchGetHashKeyField(Collection<Long> ids, String redisKey, String redisField) {
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Long>() {
			@Nullable
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				ids.stream().forEach(id -> {
					String key = RedisKeyGenerator.getKeyStr(redisKey, id.toString());
					connection.hGet(key.getBytes(), redisField.getBytes());
				});
				return null;
			}
		}, redisTemplate.getValueSerializer());
		return list;
	}

	/**
	 * 批量获取用户信息
	 */
	public List<Object> batchGetUserInfo(Collection<String> userIds) {
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Map>() {
			@Nullable
			@Override
			public Map doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				userIds.stream().forEach(userId -> {
					String key = RedisKeyGenerator.getUserInfoPrefix(userId);
					connection.hGetAll(key.getBytes());
				});
				return null;
			}
		}, redisTemplate.getValueSerializer());
		return list;
	}

	/**
	 * 批量获取用户信息
	 */
	public List<Object> batchGetUserInfoByImUser(Collection<String> userIds) {
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Map>() {
			@Nullable
			@Override
			public Map doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				userIds.stream().forEach(userId -> {
					String key = RedisKeyGenerator.getKeyStr(RedisConstant.USER_INFO_PREFIX, userId);
					connection.hGetAll(key.getBytes());
				});
				return null;
			}
		}, redisTemplate.getValueSerializer());
		return list;
	}

	public List<String> batchGetList(String key, Integer count) {
		List<Object> result = redisTemplate.executePipelined(new RedisCallback<Map>() {
			@Nullable
			@Override
			public Map doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				connection.lRange(key.getBytes(), 0, count - 1);
				connection.lTrim(key.getBytes(), count, -1);
				return null;
			}
		}, redisTemplate.getValueSerializer());
		return (List<String>) result.get(0);
	}

	public List<Object> batchGetCapitalList(Collection<Long> userIds, String redisPrefix) {
		List<Object> list = redisTemplate.executePipelined(new RedisCallback<Map>() {
			@Nullable
			@Override
			public Map doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				userIds.stream().forEach(userId -> {
					String key = RedisKeyGenerator.getKeyStr(redisPrefix, userId.toString());
					connection.get(key.getBytes());
				});
				return null;
			}
		}, redisTemplate.getValueSerializer());
		return list;
	}

	/**
	 * scan 实现
	 * 
	 * @param pattern  表达式
	 * @param consumer 对迭代到的key进行操作
	 */
	public void scan(String pattern, Consumer<byte[]> consumer) {
		this.redisTemplate.execute((RedisConnection connection) -> {
			try (Cursor<byte[]> cursor = connection
					.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
				cursor.forEachRemaining(consumer);
				return null;
			} catch (IOException e) {
				log.error(e.getMessage());
				throw new BizRuntimeException(e.getMessage());
			}
		});
	}

	/**
	 * 获取符合条件的key
	 * 
	 * @param pattern 表达式
	 * @return
	 */
	public List<String> keys(String pattern) {
		List<String> keys = Lists.newArrayList();
		this.scan(pattern, item -> {
			// 符合条件的key
			String key = new String(item, StandardCharsets.UTF_8);
			keys.add(key);
		});
		return keys;
	}
	

	// ===============================Lock=================================

	/**
	 * redis分布式加锁
	 * 
	 * @param lockKey   锁的key值
	 * @param lockValue 锁的value
	 * @param lockTime  锁时间(毫秒)
	 * @return 是否成功加锁
	 */
	public boolean lock(String lockKey, long lockValue, long lockTime) {

		boolean isLock = false;

		// 循环10次获得锁
		for (int i = 0; i < 10; i++) {
			long redisTime = currtTimeFromRedis();
			long realLockTime = redisTime + lockTime;// 超时时间

			boolean isTrue = redisTemplate.execute(new RedisCallback<Boolean>() {
				@SuppressWarnings("deprecation")
				@Override
				public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                    JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
//                    byte[] value = jdkSerializer.serialize(lockValue);

					// 定义value的序列化方式
					Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
							Object.class);
					ObjectMapper om = new ObjectMapper();
					om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
					om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
					jackson2JsonRedisSerializer.setObjectMapper(om);

					byte[] value = jackson2JsonRedisSerializer.serialize(lockValue);
					return redisConnection.setNX(lockKey.getBytes(), value);
				}
			});
			if (isTrue) {
				redisTemplate.expire(lockKey, realLockTime, TimeUnit.MILLISECONDS);
				isLock = true;
				return isLock;
			} else {
				Long curlockTime = (Long) redisTemplate.opsForValue().get(lockKey);
				if (null != curlockTime && redisTime > curlockTime) {
					Long oldLockTime = (Long) redisTemplate.opsForValue().getAndSet(lockKey, realLockTime);
					if (null != oldLockTime && oldLockTime.equals(curlockTime)) {
						redisTemplate.expire(lockKey, realLockTime, TimeUnit.MILLISECONDS);
						isLock = true;
						return isLock;
					}
				}
			}
			try {
				TimeUnit.MILLISECONDS.sleep(100);// 睡眠100毫秒
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}

		return isLock;
	}

	/**
	 * 分布式锁解锁 加锁者可解锁，非加锁者等待过期 不可解锁
	 * 
	 * @param lockKey
	 * @param lockValue
	 */
	public void unlock(String lockKey, long lockValue) {
		Long oldLockValue = (Long) redisTemplate.opsForValue().get(lockKey);
		if (null != oldLockValue && lockValue == oldLockValue) {
			delete(lockKey);
		}
	}

	// ===============================Pipeline=================================
	
	public List<Object> executePipelined(RedisCallback<List<Object>> action) {
		return redisTemplate.executePipelined(action);
	}

	/**
	 * 执行lua脚本
	 * 
	 * @param luaScript 脚本内容
	 * @param keys      redis键列表
	 * @param values    值列表
	 * @return
	 */

	public Object executeLuaScript(String luaScript, List<String> keys, List<String> values) {
		RedisScript redisScript = RedisScript.of(luaScript);
		return redisTemplate.execute(redisScript, keys, values);
	}

	/**
	 * 执行lua脚本
	 * 
	 * @param luaScript  脚本内容
	 * @param keys       redis键列表
	 * @param values     值列表
	 * @param resultType 返回值类型
	 * @return
	 */
	public <T> Object executeLuaScript(String luaScript, List<String> keys, List<String> values, Class<T> resultType) {
		RedisScript redisScript = RedisScript.of(luaScript, resultType);
		return redisTemplate.execute(redisScript, keys, values);
	}
	// ===============================Message=================================
	
	/**
	 * 发送消息
	 *
	 * @param channel
	 * @param message
	 */
	public void sendMsg(String channel, String message) {
		redisTemplate.convertAndSend(channel, message);
	}
	
	// ===============================Command=================================
	
	/**
	 * 获取redis服务器时间 保证集群环境下时间一致
	 * 
	 * @return
	 */
	
	public Long currtTimeFromRedis() {
		return redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
			return redisConnection.time();
		});
	}

	public Long dbSize() {
		return redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
			return redisConnection.dbSize();
		});
	}
	
	public Long lastSave() {
		return redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
			return redisConnection.lastSave();
		});
	}

	public void bgReWriteAof() {
		redisTemplate.execute((RedisCallback<Void>) redisConnection -> {
			redisConnection.bgReWriteAof();
			return null;
		});
	}
	
	public void bgSave() {
		redisTemplate.execute((RedisCallback<Void>) redisConnection -> {
			redisConnection.bgSave();
			return null;
		});
	}
	
	public void save() {
		redisTemplate.execute((RedisCallback<Void>) redisConnection -> {
			redisConnection.save();
			return null;
		});
	}
	
	public void flushDb() {
		redisTemplate.execute((RedisCallback<Void>) redisConnection -> {
			redisConnection.flushDb();
			return null;
		});
	}
	
	public void flushAll() {
		redisTemplate.execute((RedisCallback<Void>) redisConnection -> {
			redisConnection.flushAll();
			return null;
		});
	}
	
}