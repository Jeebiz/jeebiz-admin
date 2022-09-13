/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.logs.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.hiwepy.admin.extras.logs.setup.shiro.Slf4jMDCRequestFilter;
import io.hiwepy.boot.api.sequence.Sequence;

@Configuration
public class JeebizLogbizConfiguration implements WebMvcConfigurer {

	/*@Bean
	public Log4j2MDCInterceptor mdcInterceptor() {
		return new Log4j2MDCInterceptor();
	}*/

	@Bean("slf4j")
	public FilterRegistrationBean<Slf4jMDCRequestFilter> slf4jMDCRequestFilter(Sequence sequence) {
		FilterRegistrationBean<Slf4jMDCRequestFilter> registration = new FilterRegistrationBean<Slf4jMDCRequestFilter>();
		Slf4jMDCRequestFilter filter = new Slf4jMDCRequestFilter();
		filter.setSequence(sequence);
		registration.setFilter(filter);
		registration.setEnabled(false);
		return registration;
	}

	@Bean
	public FlywayFluentConfiguration flywayLogbizConfiguration() {

		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("logbiz",
				"日志管理-模块初始化（功能操作日志、系统异常日志、认证授权日志）", "1.0.0");

		return configuration;
	}

}
