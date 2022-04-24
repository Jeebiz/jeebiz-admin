/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@ApiModel(value = "AuthzWeixinMpBindDTO", description = "微信（公共号、服务号）登录绑定参数DTO")
@Getter
@Setter
@ToString
public class AuthzWeixinMpBindDTO extends AbstractBindDTO {
	
	/**
	 * 第三方平台Unionid（通常指第三方账号体系下用户的唯一id）
	 */
	@ApiModelProperty(name = "unionid", dataType = "String", value = "第三方平台Unionid（通常指第三方账号体系下用户的唯一id）")
	protected String unionid;
	/**
	 * 第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）
	 */
	@ApiModelProperty(name = "openid", required = true, dataType = "String", value = "第三方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）")
	protected String openid;
	/**
	 * 绑定的账号
	 */
	@ApiModelProperty(name = "username", dataType = "String", value = "绑定的账号")
	protected String username;
	/**
	 * 绑定的账号密码
	 */
	@ApiModelProperty(name = "password", dataType = "String", value = " 绑定的账号密码")
	protected String password;
	/**
	 * 微信用户信息
	 */
	@ApiModelProperty(name = "userInfo", required = true, dataType = "WxMpUser", value = "微信用户信息")
	protected WxMpUser userInfo;

}
