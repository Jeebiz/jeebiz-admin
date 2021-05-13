/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.setup.provider.FilestoreProvider;
import net.jeebiz.admin.extras.filestore.setup.provider.LocalFilestoreProvider;

@Configuration
@EnableConfigurationProperties(JeebizFilestoreProperties.class)
public class JeebizFilestoreConfiguration {

	@Bean
	public FlywayFluentConfiguration flywayFilestoreConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("fs",
				"文件存储-模块初始化（支持多种实现）", "1.0.0");
		
		return configuration;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public FilestoreProvider filestoreProvider(IFileMapper filestoreDao,
			JeebizFilestoreProperties filestoreProperties) {
		return new LocalFilestoreProvider(filestoreDao, filestoreProperties);
	}
	
}
