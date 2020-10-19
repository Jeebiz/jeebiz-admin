/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(JeebizFilestoreLocalProperties.PREFIX)
public class JeebizFilestoreLocalProperties {

	public static final String PREFIX = "local";

	/**
	 * 存储服务对外服务的主机地址或域名
	 */
	private String endpoint;
	
	/**
	 * 本地存储目录
	 */
	private String userDir = SystemUtils.getUserDir().getAbsolutePath();

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUserDir() {
		return userDir;
	}

	public void setUserDir(String userDir) {
		this.userDir = userDir;
	}

}
