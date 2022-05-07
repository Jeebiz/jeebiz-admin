/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzRoleAllotUserDTO", description = "角色分配用户参数DTO")
@Getter
@Setter
@ToString
public class AuthzRoleAllotUserDTO {

	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色id")
	private String roleId;
	/**
	 * 用户id集合
	 */
	@ApiModelProperty(name = "userIds", required = true, dataType = "java.util.List<String>", value = "用户id集合")
	private List<String> userIds;

}
