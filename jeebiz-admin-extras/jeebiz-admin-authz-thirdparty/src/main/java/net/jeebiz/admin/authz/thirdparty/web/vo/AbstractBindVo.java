/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;

@Getter
@Setter
@ToString
public abstract class AbstractBindVo {

	/**
	 * 第三方账号类型
	 */
	@ApiModelProperty(name = "type", dataType = "ThirdpartyType", value = "第三方账号类型")
	private ThirdpartyType type;
	/**
	 * 第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）
	 */
	@ApiModelProperty(name = "unionid", required = true, dataType = "String", value = "第三方平台UnionID（通常指第三方账号体系下用户的唯一ID）")
	private String unionid;
	/**
	 * 第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）
	 */
	@ApiModelProperty(name = "openid", required = true, dataType = "String", value = "第三方平台OpenID（通常指第三方账号体系下某应用中用户的唯一ID）")
	private String openid;
	/**
	 * 用户账号
	 */
	@ApiModelProperty(name = "username", required = true, dataType = "String", value = "用户账号")
	private String username;
	/**
	 * 用户密码
	 */
	@ApiModelProperty(name = "password", required = true, dataType = "String", value = "用户密码")
	private String password;

}
