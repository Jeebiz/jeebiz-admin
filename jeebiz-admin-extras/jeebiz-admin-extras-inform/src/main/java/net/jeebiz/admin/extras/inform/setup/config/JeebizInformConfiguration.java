/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizInformConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayInformConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("inform",
				"消息通知-模块初始化（系统通知和私信功能）", "1.0.0");
		
		return configuration;
	}

}
