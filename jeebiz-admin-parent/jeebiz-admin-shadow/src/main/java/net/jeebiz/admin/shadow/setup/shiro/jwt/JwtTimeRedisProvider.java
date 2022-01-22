package net.jeebiz.admin.shadow.setup.shiro.jwt;

import com.github.hiwepy.jwt.time.JwtTimeProvider;

import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

public class JwtTimeRedisProvider implements JwtTimeProvider {

	private RedisOperationTemplate redisOperationTemplate;
	
	public JwtTimeRedisProvider(RedisOperationTemplate redisOperationTemplate) {
		super();
		this.redisOperationTemplate = redisOperationTemplate;
	}

	@Override
	public long now() {
		try {
			return getRedisOperationTemplate().timeNow();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}

}
