/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.config.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JeebizConfigConfiguration implements WebMvcConfigurer {

	@Bean
	public FlywayFluentConfiguration flywayLogbizConfiguration() {

		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("config",
				"日志管理-模块初始化（功能操作日志、系统异常日志、认证授权日志）", "1.0.0");

		return configuration;
	}

}
