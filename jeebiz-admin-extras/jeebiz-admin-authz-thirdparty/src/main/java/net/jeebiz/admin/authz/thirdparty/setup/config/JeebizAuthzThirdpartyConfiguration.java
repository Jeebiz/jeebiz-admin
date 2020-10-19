/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizAuthzThirdpartyConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayAuthzThirdpartyConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("3rd",
				"第三方账号登录-模块初始化", "1.0.0");
		
		return configuration;
	}
	
}
