/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jeebiz.admin.authz.feature.setup.handler.FeatureFlatDataHandler;
import net.jeebiz.admin.authz.feature.setup.handler.FeatureTreeDataHandler;

@Configuration
public class JeebizFeatureConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayFeatureConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("feature",
				"功能菜单-模块初始化", "1.0.0");
		
		return configuration;
	}
	
	@Bean
	@ConditionalOnMissingBean(FeatureFlatDataHandler.class)
	public FeatureFlatDataHandler featureFlatDataHandler() {
		return new FeatureFlatDataHandler();
	}
	
	@Bean
	@ConditionalOnMissingBean(FeatureTreeDataHandler.class)
	public FeatureTreeDataHandler featureTreeDataHandler() {
		return new FeatureTreeDataHandler();
	}
	
}
