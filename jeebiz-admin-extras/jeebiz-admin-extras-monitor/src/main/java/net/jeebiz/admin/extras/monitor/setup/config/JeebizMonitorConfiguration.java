/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizMonitorConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayMonitorConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("monitor",
				"服务监控-模块初始化（CPU、Mem、JVM等）", "1.0.0");
		
		return configuration;
	}

}
