/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Data
@ApiModel(value = "AuthzWeixinMpCode2AccessTokenVo", description = "通过code换取accesstoken")
public class AuthzWeixinMpCode2AccessTokenVo {

	/**
	 * 第三方账号绑定状态
	 */
	@ApiModelProperty(name = "bind", dataType = "Boolean", value = "第三方账号绑定状态")
	private boolean bind;
	/**
	 * 第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）
	 */
	@ApiModelProperty(name = "unionid", dataType = "String", value = "第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）")
	private String unionid;
	/**
	 * 第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）
	 */
	@ApiModelProperty(name = "openid", dataType = "String", value = "第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）")
	private String openid;
	/**
	 * 网页授权接口调用凭证
	 */
	@ApiModelProperty(name = "accessToken", dataType = "WxMpOAuth2AccessToken", value = "网页授权接口调用凭证")
	private WxMpOAuth2AccessToken accessToken;
	/**
	 * 微信用户信息
	 */
	@ApiModelProperty(name = "userInfo", dataType = "WxMpUser", value = "微信用户信息")
	protected WxMpUser userInfo;
	
}
