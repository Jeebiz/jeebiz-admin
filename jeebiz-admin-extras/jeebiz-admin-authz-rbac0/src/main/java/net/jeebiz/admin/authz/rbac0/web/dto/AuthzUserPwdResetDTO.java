/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "AuthzUserPwdResetDTO", description = "个人密码更新参数DTO")
@Data
public class AuthzUserPwdResetDTO {

	/**
	 * 当前密码
	 */
	@ApiModelProperty(name = "oldPassword", value = "当前密码")
	@NotNull(message = "密码不能为空")
	private String oldPassword;
	/**
	 * 新密码
	 */
	@ApiModelProperty(name = "password", value = "新密码")
	@NotNull(message = "密码不能为空")
	private String password;

}
