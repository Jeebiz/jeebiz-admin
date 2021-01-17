/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.cloud.spring.boot.context.env.AliCloudProperties;
import com.aliyuncs.http.FormatType;

import lombok.Data;

@ConfigurationProperties(AliyunSmsProperties.PREFIX)
@Data
public class AliyunSmsProperties {

	public static final String PREFIX = AliCloudProperties.PROPERTY_PREFIX + ".sms";

	private FormatType acceptFormat = FormatType.JSON;
	 
	private String encoding;
	
	private Integer connectTimeout;
	
	private Integer readTimeout;
	
	private String[] signs;
	
}
