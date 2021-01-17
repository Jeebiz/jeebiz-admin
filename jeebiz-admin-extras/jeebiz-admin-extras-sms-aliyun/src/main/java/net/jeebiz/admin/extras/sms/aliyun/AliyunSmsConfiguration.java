/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.aliyun;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.spring.boot.sms.SmsServiceImpl;

@Configuration
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsConfiguration {

	@Bean
	public AliyunSmsTemplate aliyunSmsTemplate(SmsServiceImpl smsService, AliyunSmsProperties smsProperties) {
		return new AliyunSmsTemplate(smsService, smsProperties);
	}
	
}
