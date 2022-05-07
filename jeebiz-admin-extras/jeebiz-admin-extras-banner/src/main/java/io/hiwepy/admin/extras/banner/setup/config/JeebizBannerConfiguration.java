package io.hiwepy.admin.extras.banner.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JeebizBannerConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayBannerConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("banner",
				"Banner配置-模块初始化", "1.0.0");
		
		return configuration;
	}

}
