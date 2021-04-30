/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzThirdpartyDTO", description = "第三方账号登录信息DTO")
@Getter
@Setter
@ToString
public class AuthzThirdpartyDTO {

	/**
	 * 用户Id
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "用户Id")
	private String uid;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "用户名称")
	private String uname;
	/**
	 * 第三方账号登录表id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "第三方账号登录表id")
	private String id;

	/**
	 * 第三方账号类型：（qq：腾讯QQ，wx：微信）
	 */
	@ApiModelProperty(name = "type", required = true, dataType = "String", value = "第三方账号类型：（qq：腾讯QQ，wx：微信）")
	private String type;
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
	 * 当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）
	 */
	@ApiModelProperty(name = "devid", dataType = "String", value = "当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）")
	private String devid;
	/**
	 * 第三方认证账号扩展信息
	 */
	@ApiModelProperty(name = "rawdata", dataType = "String", value = "第三方认证账号扩展信息")
	private String rawdata;
	/**
	 * 第三方账号绑定时间
	 */
	@ApiModelProperty(name = "time24",  dataType = "String", value = "第三方账号绑定时间")
	private String time24;

}
