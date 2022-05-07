package io.hiwepy.admin.shadow.setup.config;

import org.apache.shiro.spring.boot.FilterChainDefinitionConfigurer;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.springframework.stereotype.Component;

@Component
public class ShrioFilterChainDefinitionConfigurer implements FilterChainDefinitionConfigurer {

	@Override
	public void configurePathDefinition(DefaultShiroFilterChainDefinition chainDefinition) {
		chainDefinition.addPathDefinition("/*", "headers,jwt");
		chainDefinition.addPathDefinition("/**", "headers,jwt");
	}
	
}
