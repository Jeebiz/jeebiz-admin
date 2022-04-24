/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzWeixinCode2SessionDTO", description = "微信小程序codeToSession返回传输对象实体")
@Data
public class AuthzWeixinCode2SessionDTO {

	/**
	 * 会话密钥
	 */
	@ApiModelProperty(name = "sessionKey", dataType = "String", value = "会话密钥")
	private String sessionKey;
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
	 * 第三方账号绑定状态
	 */
	@ApiModelProperty(name = "bind", dataType = "Boolean", value = "第三方账号绑定状态")
	private boolean bind;
	
}
