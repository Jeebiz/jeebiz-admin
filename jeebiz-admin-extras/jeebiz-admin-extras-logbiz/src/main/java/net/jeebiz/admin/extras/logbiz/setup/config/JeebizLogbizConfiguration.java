/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.jeebiz.admin.extras.logbiz.setup.shiro.Slf4jMDCRequestFilter;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.api.web.servlet.handler.Slf4jMDCInterceptor;

@Configuration
public class JeebizLogbizConfiguration implements WebMvcConfigurer {
	
	@Bean
	public Slf4jMDCInterceptor mdcInterceptor() {
		return new Slf4jMDCInterceptor();
	}
	
	/*@Bean
	public Log4j2MDCInterceptor mdcInterceptor() {
		return new Log4j2MDCInterceptor();
	}*/

	@Bean("log4j2")
	public FilterRegistrationBean<Slf4jMDCRequestFilter> slf4jMDCRequestFilter(Sequence sequence) {
		FilterRegistrationBean<Slf4jMDCRequestFilter> registration = new FilterRegistrationBean<Slf4jMDCRequestFilter>();
		Slf4jMDCRequestFilter filter = new Slf4jMDCRequestFilter();
		filter.setSequence(sequence);
		registration.setFilter(filter);
		registration.setEnabled(false);
		return registration;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(mdcInterceptor()).addPathPatterns("/**").order(Integer.MIN_VALUE);
	}
	
	@Bean
	public FlywayFluentConfiguration flywayLogbizConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("logbiz",
				"日志管理-模块初始化（功能操作日志、系统异常日志、认证授权日志）", "1.0.0");
		
		return configuration;
	}

}
