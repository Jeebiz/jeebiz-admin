package net.jeebiz.admin.authz.jwt.setup.shiro;

import com.github.hiwepy.jwt.time.JwtTimeProvider;

import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;

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
