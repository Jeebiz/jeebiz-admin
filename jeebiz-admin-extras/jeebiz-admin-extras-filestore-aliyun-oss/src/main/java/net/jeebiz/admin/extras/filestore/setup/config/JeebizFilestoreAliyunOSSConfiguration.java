/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.spring.boot.oss.OssConstants;
import com.aliyun.oss.OSS;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.setup.provider.AliyunOssFilestoreProvider;
import net.jeebiz.admin.extras.filestore.setup.provider.AliyunOssTemplate;
import net.jeebiz.admin.extras.filestore.setup.provider.FilestoreProvider;

/**
 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
@AutoConfigureAfter(name = {"com.alibaba.cloud.spring.boot.oss.autoconfigure.OssContextAutoConfiguration"})
@Configuration
@ConditionalOnProperty(name = OssConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ AliyunOssProperties.class })
public class JeebizFilestoreAliyunOSSConfiguration {

	@Bean
	public AliyunOssFilestoreProvider aliyunOssFilestoreProvider(IFilestoreDao filestoreDao, OSS ossClient,
			AliyunOssProperties ossProperties, AliyunOssTemplate ossTemplate) {
		return new AliyunOssFilestoreProvider(filestoreDao, ossClient, ossProperties, ossTemplate);
	}
	
	@Bean
	public AliyunOssTemplate aliyunOssTemplate(OSS ossClient) {
		return new AliyunOssTemplate(ossClient);
	}
	
	@Bean
	public FilestoreProvider filestoreProvider(IFilestoreDao filestoreDao, 
			OSS ossClient,
			AliyunOssProperties ossProperties, 
			AliyunOssTemplate ossTemplate) {
		return new AliyunOssFilestoreProvider(filestoreDao, ossClient, ossProperties, ossTemplate);
	}
	
}
