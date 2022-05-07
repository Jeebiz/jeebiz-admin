/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.cnarea.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizCnareaConfiguration {

	@Bean
	public FlywayFluentConfiguration flywayCnareaConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("cnarea",
				"中国区域数据-模块初始化", "1.0.0");
		
		return configuration;
	}
	
}
