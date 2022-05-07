/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.dict.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperationTemplate;

import io.hiwepy.admin.extras.dict.dao.DictMapper;
import io.hiwepy.admin.extras.dict.service.IDictPairService;
import io.hiwepy.admin.extras.dict.setup.DictRedisTemplate;
import io.hiwepy.admin.extras.dict.setup.provider.MapKeyValueProvider;
import io.hiwepy.admin.extras.dict.setup.provider.StringKeyValueProvider;

@Configuration
public class JeebizDictConfiguration {

	@Bean
	public FlywayFluentConfiguration flywayDictConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("dict",
				"数据字典-模块初始化", "1.0.0");
		
		return configuration;
	}

	@Bean
	public DictRedisTemplate dictRedisTemplate(RedisOperationTemplate redisOperation,
			DictMapper dictMapper) {
		return new DictRedisTemplate(redisOperation, dictMapper);
	}
	
	@Bean
	public MapKeyValueProvider mapKeyValueProvider(IDictPairService keyValueService) {
		return new MapKeyValueProvider( keyValueService );
	}
	
	@Bean
	public StringKeyValueProvider stringKeyValueProvider(IDictPairService keyValueService) {
		return new StringKeyValueProvider( keyValueService);
	}
	
	
}
