/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzUserRegisterVo", description = "用户注册信息参数Vo")
@Getter
@Setter
@ToString
public class AuthzUserRegisterVo {

	/**
	 * 身份证件号码
	 */
	@ApiModelProperty(name = "idcard", required = true, dataType = "String", value = "身份证件号码")
	@NotBlank(message = "身份证件号必选")
	private String idcard;
	/**
	 * 用户姓名（昵称）
	 */
	@ApiModelProperty(name = "alias", required = true, dataType = "String", value = "用户姓名")
	@NotBlank(message = "姓名必填")
	private String alias;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(name = "phone", required = true, dataType = "String", value = "手机号码")
	@NotBlank(message = "手机号码必填")
	private String phone;
	/**
	 * 账户密码
	 */
	@ApiModelProperty(name = "password", required = true, dataType = "String", value = "账户密码")
	@NotBlank(message = "账户密码必选")
	private String password;

}
