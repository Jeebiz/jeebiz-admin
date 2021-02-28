/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzUserRenewDTO", description = "用户详细信息更新参数DTO")
@Data
public class AuthzUserRenewDTO {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构ID编号")
	private String orgId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门ID编号")
	private String deptId;
	/**
	 * 岗位ID编号
	 */
	@ApiModelProperty(name = "postId", dataType = "String", value = "岗位ID编号")
	private String postId;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "用户ID")
	@NotBlank(message = "用户ID必填")
	private String id;
	/**
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "ucode", required = true, dataType = "String", value = "用户唯一编号（工号）")
	@NotBlank(message = "用户唯一编号（工号）必填")
	private String ucode;
	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色ID")
	@NotBlank(message = "角色必选")
	private String roleId;
	

}
