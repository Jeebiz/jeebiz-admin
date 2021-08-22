/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSS;

import net.jeebiz.admin.extras.filestore.strategy.AliyunOssTemplate;

/**
 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
@AutoConfigureAfter(name = {"com.alibaba.cloud.spring.boot.oss.autoconfigure.OssContextAutoConfiguration"})
@Configuration
@EnableConfigurationProperties({ AliyunOssProperties.class })
public class JeebizFilestoreAliyunOSSConfiguration {
	
	@Bean
	public AliyunOssTemplate aliyunOssTemplate(OSS ossClient) {
		return new AliyunOssTemplate(ossClient);
	}
	
}
