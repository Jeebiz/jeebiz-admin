/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzRoleAllotPermsVo", description = "角色分配权限参数Vo")
@Getter
@Setter
@ToString
public class AuthzRoleAllotPermsVo {
	
	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "roleId", dataType = "String", value = "角色ID")
	private String roleId;
	/**
	 * 角色授权的标记集合
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
	private List<String> perms = Lists.newArrayList();

}
