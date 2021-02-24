/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.config;

import org.apache.logging.log4j.spring.boot.ext.web.Log4j2MDCInterceptor;
import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.jeebiz.admin.extras.logbiz.setup.shiro.Log4j2MDCRequestFilter;

@Configuration
public class JeebizLogbizConfiguration implements WebMvcConfigurer {
	
	@Autowired
	private Log4j2MDCInterceptor mdcInterceptor;
	
	@Bean
	public Log4j2MDCInterceptor mdcInterceptor() {
		return new Log4j2MDCInterceptor();
	}

	@Bean("log4j2")
	public FilterRegistrationBean<Log4j2MDCRequestFilter> log4j2MDCRequestFilter() {
		FilterRegistrationBean<Log4j2MDCRequestFilter> registration = new FilterRegistrationBean<Log4j2MDCRequestFilter>();
		registration.setFilter(new Log4j2MDCRequestFilter());
		registration.setEnabled(false);
		return registration;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(mdcInterceptor).addPathPatterns("/**").order(Integer.MIN_VALUE);
	}
	
	@Bean
	public FlywayFluentConfiguration flywayLogbizConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("logbiz",
				"日志管理-模块初始化（功能操作日志、系统异常日志、认证授权日志）", "1.0.0");
		
		return configuration;
	}

}
