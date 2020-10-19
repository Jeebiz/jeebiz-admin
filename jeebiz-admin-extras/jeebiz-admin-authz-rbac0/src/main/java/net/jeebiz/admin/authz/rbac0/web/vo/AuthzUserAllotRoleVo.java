/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzUserAllotRoleVo", description = "用户分配角色参数Vo")
@Getter
@Setter
@ToString
public class AuthzUserAllotRoleVo {

	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色ID")
	private String roleId;
	/**
	 * 用户ID集合
	 */
	@ApiModelProperty(name = "userIds", required = true, dataType = "java.util.List<String>", value = "用户ID集合")
	private List<String> userIds;

}
