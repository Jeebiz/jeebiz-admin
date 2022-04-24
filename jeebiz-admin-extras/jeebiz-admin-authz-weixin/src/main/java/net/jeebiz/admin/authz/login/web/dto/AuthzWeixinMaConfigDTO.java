/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthzWeixinMaConfigDTO", description = "微信（小程序）配置对象DTO")
public class AuthzWeixinMaConfigDTO {

	/**
	 * 公众号的唯一标识
	 */
	@ApiModelProperty(name = "appId", dataType = "String", value = "公众号的唯一标识")
	private String appId;
	/**
	 * 生成签名的随机串
	 */
	@ApiModelProperty(name = "nonceStr", dataType = "String", value = "生成签名的随机串")
	private String nonceStr;
	/**
	 * 签名
	 */
	@ApiModelProperty(name = "signature", dataType = "String", value = "签名")
	private String signature;
	/**
	 * 生成签名的时间戳
	 */
	@ApiModelProperty(name = "timestamp", dataType = "Long", value = "生成签名的时间戳")
	private long timestamp;
	/**
	 * 消息模板id
	 */
	@ApiModelProperty(name = "tmplIds", dataType = "String[]", value = "消息模板id")
	private String[] tmplIds;
	
}
