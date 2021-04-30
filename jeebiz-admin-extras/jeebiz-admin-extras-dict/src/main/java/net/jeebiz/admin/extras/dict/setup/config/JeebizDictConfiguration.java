/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import net.jeebiz.admin.extras.dict.dao.IDictDao;
import net.jeebiz.admin.extras.dict.service.IKeyValueService;
import net.jeebiz.admin.extras.dict.setup.DictRedisTemplate;
import net.jeebiz.admin.extras.dict.setup.provider.MapKeyValueProvider;
import net.jeebiz.admin.extras.dict.setup.provider.StringKeyValueProvider;

@Configuration
public class JeebizDictConfiguration {

	@Bean
	public FlywayFluentConfiguration flywayDictConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("dict",
				"数据字典-模块初始化", "1.0.0");
		
		return configuration;
	}

	@Bean
	public DictRedisTemplate dictRedisTemplate(RedisTemplate<String, Object> redisTemplate,
			IDictDao dictDao) {
		return new DictRedisTemplate(redisTemplate, dictDao);
	}
	
	@Bean
	public MapKeyValueProvider mapKeyValueProvider(IKeyValueService keyValueService) {
		return new MapKeyValueProvider( keyValueService );
	}
	
	@Bean
	public StringKeyValueProvider stringKeyValueProvider(IKeyValueService keyValueService) {
		return new StringKeyValueProvider( keyValueService);
	}
	
	
}
