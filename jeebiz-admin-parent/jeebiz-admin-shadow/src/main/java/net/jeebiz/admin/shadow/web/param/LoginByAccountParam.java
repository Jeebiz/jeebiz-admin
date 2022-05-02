package net.jeebiz.admin.shadow.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账号登录参数
 */
@Data
public class LoginByAccountParam {
	
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
