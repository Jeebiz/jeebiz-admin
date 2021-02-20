/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.goeasy.GoEasy;
import net.jeebiz.admin.extras.inform.goeasy.InformGoEasyOutputProvider;

@Configuration
public class JeebizInformGoEasyConfiguration {
	
	@Bean
	public InformGoEasyOutputProvider informGoEasyOutputProvider(GoEasy goEasy) {
		return new InformGoEasyOutputProvider(goEasy);
	}

}
