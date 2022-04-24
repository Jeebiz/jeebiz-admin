/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;

@ApiModel(value = "AuthzWeixinMpCode2AccessTokenDTO", description = "通过code换取accesstoken")
@Data
public class AuthzWeixinMpCode2AccessTokenDTO {

	/**
	 * 第三方账号绑定状态
	 */
	@ApiModelProperty(name = "bind", dataType = "Boolean", value = "第三方账号绑定状态")
	private boolean bind;
	/**
	 * 第三方平台Unionid（通常指第三方账号体系下用户的唯一id）
	 */
	@ApiModelProperty(name = "unionid", dataType = "String", value = "第三方平台Unionid（通常指第三方账号体系下用户的唯一id）")
	private String unionid;
	/**
	 * 第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）
	 */
	@ApiModelProperty(name = "openid", dataType = "String", value = "第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）")
	private String openid;
	/**
	 * 网页授权接口调用凭证
	 */
	@ApiModelProperty(name = "accessToken", dataType = "WxOAuth2AccessToken", value = "网页授权接口调用凭证")
	private WxOAuth2AccessToken accessToken;
	/**
	 * 微信用户信息
	 */
	@ApiModelProperty(name = "userInfo", dataType = "WxOAuth2UserInfo", value = "微信用户信息")
	private WxOAuth2UserInfo userInfo;
	
}
