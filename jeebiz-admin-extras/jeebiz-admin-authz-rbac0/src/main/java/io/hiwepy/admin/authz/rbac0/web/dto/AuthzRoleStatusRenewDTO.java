/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.boot.api.annotation.AllowableValues;

@ApiModel(value = "AuthzRoleStatusRenewDTO", description = "角色状态更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzRoleStatusRenewDTO {

	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色id")
	@NotBlank(message = "角色id必填")
	private String id;
	/**
	 * 角色状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "角色状态（0:禁用|1:可用）", allowableValues = "0,1")
	@NotBlank(message = "角色状态必填")
	@AllowableValues(allows = "0,1")
	private String status;

}
