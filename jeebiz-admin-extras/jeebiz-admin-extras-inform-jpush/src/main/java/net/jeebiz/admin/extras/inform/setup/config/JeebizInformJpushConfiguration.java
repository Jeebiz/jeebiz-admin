/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.jpush.spring.boot.JPushTemplate;
import net.jeebiz.admin.extras.inform.jpush.InformJpushOutputProvider;

@Configuration
public class JeebizInformJpushConfiguration {
	
	@Bean
	public InformJpushOutputProvider informJpushOutputProvider(JPushTemplate jPushTemplate) {
		return new InformJpushOutputProvider(jPushTemplate);
	}

}
