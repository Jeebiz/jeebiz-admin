/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.spring.boot.FastdfsProperties;
import com.github.tobato.fastdfs.spring.boot.FastdfsTemplate;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.setup.provider.FastdfsFilestoreProvider;


/**
 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
@AutoConfigureAfter(name = {"com.github.tobato.fastdfs.spring.boot.FastdfsAutoAutoConfiguration"})
@Configuration
@ConditionalOnProperty(prefix = FastdfsProperties.PREFIX, value = "enabled", havingValue = "true")
public class JeebizFilestoreFastDFSConfiguration {
  
	@Bean
	public FastdfsFilestoreProvider fastdfsFilestoreProvider(IFilestoreDao filestoreDao, FastFileStorageClient fdfsStorageClient,
			FastdfsTemplate fdfsTemplate) {
		return new FastdfsFilestoreProvider(filestoreDao, fdfsStorageClient, fdfsTemplate);
	}
	
}
