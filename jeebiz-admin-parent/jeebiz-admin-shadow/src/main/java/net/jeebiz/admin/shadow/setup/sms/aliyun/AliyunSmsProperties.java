/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.sms.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.aliyuncs.http.FormatType;

import lombok.Data;

@ConfigurationProperties(AliyunSmsProperties.PREFIX)
@Data
public class AliyunSmsProperties {

	public static final String PREFIX = "spring.cloud.alicloud.sms";

	/**
	 * Enable Aliyun Sms .
	 */
	private boolean enable = false;
	
	private FormatType acceptFormat = FormatType.JSON;
	 
	private String encoding;
	
	private Integer connectTimeout;
	
	private Integer readTimeout;
	
	private String[] signs;
	
}
