/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.feature.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizFeatureConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayFeatureConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("feature",
				"功能菜单-模块初始化", "1.0.0");
		
		return configuration;
	}
		
}
