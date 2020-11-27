/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzWeixinMpConfigDTO", description = "微信（公共号、服务号）配置对象DTO")
@Data
public class AuthzWeixinMpConfigDTO {

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
	   * 第三方使用网站应用授权登录的url
	   * 构造.
	   * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN">网站应用微信登录开发指南</a>
	 * URL格式为：https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	 */
	@ApiModelProperty(name = "qrconnect", dataType = "String", value = "第三方使用网站应用授权登录的url")
	private String qrconnect;
	/**
	 * 消息模板ID
	 */
	@ApiModelProperty(name = "tmplIds", dataType = "String[]", value = "消息模板ID")
	private String[] tmplIds;
	
}
