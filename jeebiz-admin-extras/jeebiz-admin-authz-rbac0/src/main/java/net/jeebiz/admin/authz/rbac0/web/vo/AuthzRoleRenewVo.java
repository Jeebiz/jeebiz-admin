/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.annotation.AllowableValues;

@ApiModel(value = "AuthzRoleRenewVo", description = "角色信息更新参数Vo")
@Getter
@Setter
@ToString
public class AuthzRoleRenewVo {

	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色ID")
	@NotBlank(message = "角色ID必填")
	
	private String id;
	/**
	 * 角色名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "角色名称")
	@NotBlank(message = "角色名称必填")
	
	private String name;
	/**
	 * 角色简介
	 */
	@ApiModelProperty(name = "intro", required = true, dataType = "String", value = "角色简介")
	@NotBlank(message = "角色简介必填")
	
	private String intro;
	/**
	 * 角色状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "角色状态（0:禁用|1:可用）", allowableValues = "0,1")
	@NotBlank(message = "角色状态必填")
	
	@AllowableValues(allows = "0,1")
	private String status;
	/**
	 * 角色授权的标记集合
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
	@NotNull(message = "至少需要一条角色授权标记")
	private List<String> perms = Lists.newArrayList();

}
