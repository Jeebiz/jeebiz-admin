/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.setup.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.admin.extras.filestore.web.vo.FilestoreConfig;

@ApiModel(value = "AliyunOssFilestoreConfig", description = "阿里云对外Oss公开配置")
@Data
@EqualsAndHashCode(callSuper=false)
public class AliyunOssFilestoreConfig extends FilestoreConfig {

	@ApiModelProperty(name = "bucketName", dataType = "String", value = "Bucket 名称")
	private String bucketName;
	@ApiModelProperty(name = "expiration", dataType = "String", value = "Token 过期时间")
    private String expiration;
	@ApiModelProperty(name = "accessKey", dataType = "String", value = "阿里云访问key")
    private String accessKey;
	@ApiModelProperty(name = "accessKeySecret", dataType = "String", value = "阿里云访问密钥")
    private String accessKeySecret;
	@ApiModelProperty(name = "securityToken", dataType = "String", value = "Token")
    private String securityToken;
	
}
