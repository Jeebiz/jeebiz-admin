/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.article.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizArticleConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayArticleConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("article",
				"通知公告管理-模块初始化", "1.0.0");
		
		return configuration;
	}
	
}
