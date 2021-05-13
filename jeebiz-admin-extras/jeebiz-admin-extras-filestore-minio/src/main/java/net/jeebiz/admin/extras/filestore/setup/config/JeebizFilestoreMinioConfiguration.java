/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import io.minio.spring.boot.MinioAutoConfiguration;
import io.minio.spring.boot.MinioProperties;
import net.jeebiz.admin.extras.filestore.dao.IFileMapper;
import net.jeebiz.admin.extras.filestore.setup.provider.MinioFilestoreProvider;


/**
 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
@ConditionalOnProperty(prefix = MinioProperties.PREFIX, value = "enabled", havingValue = "true")
@AutoConfigureAfter(MinioAutoConfiguration.class)
public class JeebizFilestoreMinioConfiguration {

	@Bean
	public MinioFilestoreProvider minioFilestoreProvider(IFileMapper fileMapper, MinioClient minioClient,
			MinioProperties minioProperties) {
		return new MinioFilestoreProvider(fileMapper, minioClient, minioProperties);
	}
	
}
