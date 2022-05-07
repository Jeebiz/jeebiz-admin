package io.hiwepy.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 */
@Data
public class LoginByThirdParam {
	
	/**
	 * 3方令牌
	 */
	@ApiModelProperty(name = "accessToken", required = true, value = "3方令牌")
	private String accessToken;
	/**
	 * 3方平台Unionid（通常指第三方账号体系下用户的唯一id）
	 */
	@ApiModelProperty(value = "3方平台Unionid", required = false)
	private String unionid;
	/**
	 * 3方平台Openid（通常指第三方账号体系下某应用中用户的唯一id）
	 */
	@ApiModelProperty(value = "3方平台Openid", required = false)
	private String openid;
	/**
	 * 当前认证对接平台开发者账号id（通常指与第三方平台进行认证对接的开发者账号的唯一id）
	 */
	@ApiModelProperty(value = "3方开发者账号id", required = false)
	private String devid;
	/**
	 * 第三方认证账号扩展信息
	 */
	@ApiModelProperty(value = "扩展信息", required = false)
	private String rawdata;
	/**
	 * 绑定第三方时登陆人token
	 */
	@ApiModelProperty(name = "token", required = true, value = "绑定第三方时登陆人token")
	private String token;
	
	
}
