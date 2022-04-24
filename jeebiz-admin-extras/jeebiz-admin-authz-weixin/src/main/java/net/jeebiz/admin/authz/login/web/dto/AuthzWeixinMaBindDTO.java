/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.web.dto;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzWeixinMaBindDTO", description = "微信（小程序）登录绑定参数DTO")
@Getter
@Setter
@ToString
public class AuthzWeixinMaBindDTO extends AbstractBindDTO {
	
	/**
	 * 第三方平台js-sdk获取的编码
	 */
	@ApiModelProperty(name = "jscode", required = true, dataType = "String", value = "第三方平台js-sdk获取的编码")
	private String jscode;
	/**
	 * 会话密钥
	 */
	@ApiModelProperty(name = "sessionKey", dataType = "String", value = "会话密钥")
	private String sessionKey;
	/**
	 * 原始数据字符串
	 */
	@ApiModelProperty(name = "signature", required = true, dataType = "String", value = "原始数据字符串")
	protected String signature;
	/**
	 * 校验用户信息字符串
	 */
	@ApiModelProperty(name = "rawData", required = true, dataType = "String", value = "校验用户信息字符串")
	protected String rawData;
	/**
	 * 加密用户数据
	 */
	@ApiModelProperty(name = "encryptedData", required = true, dataType = "String", value = "加密用户数据")
	protected String encryptedData;
	/**
	 * 加密算法的初始向量
	 */
	@ApiModelProperty(name = "iv", required = true, dataType = "String", value = " 加密算法的初始向量")
	protected String iv;
	/**
	 * 用户信息
	 */
	@ApiModelProperty(name = "userInfo", dataType = "WxMaUserInfo", value = "前端获取到的用户信息")
	protected WxMaUserInfo userInfo;

}
