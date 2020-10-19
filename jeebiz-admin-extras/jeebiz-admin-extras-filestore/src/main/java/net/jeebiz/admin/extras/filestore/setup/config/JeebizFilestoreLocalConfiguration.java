/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.setup.provider.LocalFilestoreProvider;


/**
 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
@Configuration
@EnableConfigurationProperties(JeebizFilestoreLocalProperties.class)
public class JeebizFilestoreLocalConfiguration {

	@Bean
	public LocalFilestoreProvider localFilestoreProvider(IFilestoreDao filestoreDao,
			JeebizFilestoreLocalProperties filestoreProperties) {
		return new LocalFilestoreProvider(filestoreDao, filestoreProperties);
	}
	
}
