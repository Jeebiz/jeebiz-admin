/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.device.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizDeviceConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayArticleConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("device",
				"设备激活-模块初始化", "1.0.0");
		
		return configuration;
	}
	
}
