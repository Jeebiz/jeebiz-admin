package io.hiwepy.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账号密码登录参数
 */
@Data
public class LoginByPasswordParam {
	/**
	 * 账号
	 */
	@ApiModelProperty(name = "account", required = true, value = "账号")
	private String account;

	/**
	 * 密码
	 */
	@ApiModelProperty(name = "password", required = true, value = "密码")
	private String password;
}
