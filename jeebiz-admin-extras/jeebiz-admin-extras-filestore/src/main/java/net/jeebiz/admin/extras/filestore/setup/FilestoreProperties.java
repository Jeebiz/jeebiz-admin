/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup;

import org.springframework.boot.context.properties.ConfigurationProperties;

import net.jeebiz.admin.extras.filestore.setup.provider.FilestoreEnum;

@ConfigurationProperties(FilestoreProperties.PREFIX)
public class FilestoreProperties {

	public static final String PREFIX = "file";
	
	/**
	 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
	 */
	private FilestoreEnum storage = FilestoreEnum.LOCAL;
	
	public FilestoreEnum getStorage() {
		return storage;
	}
	
	public void setStorage(FilestoreEnum storage) {
		this.storage = storage;
	}

}
