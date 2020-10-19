package net.jeebiz.admin.extras.core.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * J2Cache 配置 
 * https://gitee.com/ld/J2Cache/tree/master/modules/spring-boot2-starter
 */
@Configuration
public class J2cacheCachingConfiguration {
	 
	@Bean("j2CacheValueSerializer")
	public RedisSerializer<Object> j2CacheValueSerializer() {
		return new Jackson2JsonRedisSerializer<>(Object.class);
	}
	
}