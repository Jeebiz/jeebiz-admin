/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.dto;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzRoleAllotPermsDTO", description = "角色分配权限参数DTO")
@Getter
@Setter
@ToString
public class AuthzRoleAllotPermsDTO {
	
	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "roleId", dataType = "String", value = "角色id")
	private String roleId;
	/**
	 * 角色授权的标记集合
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
	private List<String> perms = Lists.newArrayList();

}
