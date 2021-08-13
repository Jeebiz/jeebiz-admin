package net.jeebiz.admin.shadow.setup.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import net.jeebiz.admin.extras.authz.feature.setup.handler.FeatureDataHandlerFactory;
import net.jeebiz.admin.shadow.setup.handler.LayuiATreeFeatureHandler;
import net.jeebiz.admin.shadow.setup.handler.LayuiAuthTreeFeatureHandler;

@Configuration
public class ExtrasConfig {

	@PostConstruct
	public void initExtras() {
		FeatureDataHandlerFactory.newHandler("atree", new LayuiATreeFeatureHandler());
		FeatureDataHandlerFactory.newHandler("authtree", new LayuiAuthTreeFeatureHandler());
	}
	
}
