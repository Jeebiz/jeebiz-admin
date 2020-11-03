package net.jeebiz.admin.shadow.setup.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.alicloud.sms.SmsServiceImpl;

import net.jeebiz.admin.shadow.setup.sms.aliyun.AliyunSmsProperties;
import net.jeebiz.admin.shadow.setup.sms.aliyun.AliyunSmsTemplate;

@Configuration
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsConfiguration {

	@Bean
	public AliyunSmsTemplate aliyunSmsTemplate(SmsServiceImpl smsService, AliyunSmsProperties smsProperties) {
		return new AliyunSmsTemplate(smsService, smsProperties);
	}
	
}
