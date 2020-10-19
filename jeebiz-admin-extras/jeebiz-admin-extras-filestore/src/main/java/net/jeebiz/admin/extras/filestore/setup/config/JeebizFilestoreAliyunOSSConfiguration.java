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

import com.aliyun.oss.OSS;
import com.aliyun.oss.spring.boot.OssConstants;
import com.aliyun.oss.spring.boot.OssProperties;
import com.aliyun.oss.spring.boot.OssTemplate;

import net.jeebiz.admin.extras.filestore.dao.IFilestoreDao;
import net.jeebiz.admin.extras.filestore.setup.FilestoreProperties;
import net.jeebiz.admin.extras.filestore.setup.provider.AliyunOssFilestoreProvider;

/**
 * 存储目标（local:服务本地,fdfs:FastDFS存储服务,minio:MinIO对象存储）
 */
@AutoConfigureAfter(name = {"com.aliyun.oss.spring.boot.OssAutoConfiguration"})
@Configuration
@ConditionalOnProperty(name = OssConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ FilestoreProperties.class })
public class JeebizFilestoreAliyunOSSConfiguration {

	@Bean
	public AliyunOssFilestoreProvider aliyunOssFilestoreProvider(IFilestoreDao filestoreDao, OSS ossClient,
			OssProperties ossProperties, OssTemplate ossTemplate) {
		return new AliyunOssFilestoreProvider(filestoreDao, ossClient, ossProperties, ossTemplate);
	}

}
