/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthzWeixinMaConfigVo", description = "微信（小程序）配置对象Vo")
public class AuthzWeixinMaConfigVo {

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
	 * 消息模板ID
	 */
	@ApiModelProperty(name = "tmplIds", dataType = "String[]", value = "消息模板ID")
	private String[] tmplIds;
	
}
