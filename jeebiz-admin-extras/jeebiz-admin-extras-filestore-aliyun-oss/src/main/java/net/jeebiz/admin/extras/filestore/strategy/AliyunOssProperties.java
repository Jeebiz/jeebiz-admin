/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jeebiz.admin.extras.filestore.strategy;

import static com.alibaba.cloud.spring.boot.oss.OssConstants.PREFIX;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.cloud.context.AliCloudAuthorizationMode;
import com.alibaba.cloud.spring.boot.oss.OssConstants;
import com.aliyun.oss.ClientBuilderConfiguration;

import lombok.Data;


/**
 * {@link ConfigurationProperties} for configuring OSS.
 */
@ConfigurationProperties(OssConstants.PREFIX)
@Data
public class AliyunOssProperties {

	private boolean enabled;
	
    /**
     * Authorization Mode, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    @Value("${" + PREFIX + ".authorization-mode:AK_SK}")
    private AliCloudAuthorizationMode authorizationMode;

    /**
     * Endpoint, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    private String endpoint;
    
	private String bucket;
    
    private String bucketName = "<default>";

    /**
     * alibaba cloud access key.
     */
    private String accessKey;

    /**
     * alibaba cloud secret key.
     */
    private String secretKey;
    
    /**
     * Sts token, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    private StsToken sts;

    /**
     * Client Configuration, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    private ClientBuilderConfiguration config;

    @Data
    public static class StsToken {

    	/**
         * STS Endpoint, please see <a href=
         * "https://help.aliyun.com/document_detail/28756.html?spm=a2c4g.11186623.6.791.62d327f1QNuJxZ">STS
         * Docs</a>.
         */
        private String endpoint;
        
        private String accessKey;

        private String secretKey;

        private String securityToken;

        private String roleArn = "<role-arn>";
    	
        private String roleSessionName = "<session-name>";

    }

}
