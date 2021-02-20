/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dingtalk.spring.boot.DingTalkTemplate;

import net.jeebiz.admin.extras.inform.dingtalk.InformDingtalkOutputProvider;

@Configuration
public class JeebizInformDingtalkConfiguration {
	
	@Bean
	public InformDingtalkOutputProvider informDingtalkOutputProvider(DingTalkTemplate dingTalkTemplate) {
		return new InformDingtalkOutputProvider(dingTalkTemplate);
	}

}
