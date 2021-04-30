/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzUserNewDTO", description = "新增用户详细信息参数DTO")
@Data
public class AuthzUserNewDTO {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构id编号")
	private String orgId;
	/**
	 * 部门id编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门id编号")
	private String deptId;
	/**
	 * 岗位id编号
	 */
	@ApiModelProperty(name = "postId", dataType = "String", value = "岗位id编号")
	private String postId;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username", required = true, dataType = "String", value = "用户名")
	@NotBlank(message = "用户名必填")
	private String username;
	/**
	 * 默认密码
	 */
	@ApiModelProperty(name = "password", required = true, dataType = "String", value = "默认密码")
	@NotBlank(message = "默认密码必填")
	private String password;
	/**
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "ucode", required = true, dataType = "String", value = "用户唯一编号（工号）")
	@NotBlank(message = "用户唯一编号（工号）必填")
	private String ucode;
	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色id")
	@NotBlank(message = "角色必选")
	private String roleId;
	/**
	 * 用户详情信息
	 */
	@ApiModelProperty(name = "profile", required = true, dataType = "AuthzUserProfileNewDTO", value = "用户详情信息")
	private AuthzUserProfileNewDTO profile;
	
}
