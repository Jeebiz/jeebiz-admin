/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UserPwdResetDTO", description = "个人密码更新参数DTO")
@Data
public class UserPwdResetDTO {

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
