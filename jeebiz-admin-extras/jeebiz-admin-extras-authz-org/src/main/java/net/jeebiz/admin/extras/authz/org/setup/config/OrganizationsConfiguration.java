package net.jeebiz.admin.extras.authz.org.setup.config;

import org.flywaydb.spring.boot.ext.FlywayFluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationsConfiguration {
	
	@Bean
	public FlywayFluentConfiguration flywayOrgConfiguration() {
		
		FlywayFluentConfiguration configuration = new FlywayFluentConfiguration("org",
				"组织架构-模块初始化", "1.0.0");
		
		return configuration;
	}
	
	
}
