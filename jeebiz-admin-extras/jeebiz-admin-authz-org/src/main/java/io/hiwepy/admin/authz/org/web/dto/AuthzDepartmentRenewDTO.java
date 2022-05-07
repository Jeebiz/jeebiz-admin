/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentRenewDTO", description = "部门信息更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzDepartmentRenewDTO implements Serializable {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构id编号")
	@NotBlank(message = "机构id编号必填")
	
	private String orgId;
	/**
	 * 部门id编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "部门id编号")
	@NotBlank(message = "部门id编号")
	
	private String id;
	/**
	 * 部门编码
	 */
	@ApiModelProperty(name = "code", required = true, dataType = "String", value = "部门编码")
	@NotBlank(message = "部门编码必填")
	
	private String code;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "部门名称")
	@NotBlank(message = "部门名称必填")
	
	private String name;
	/**
	 * 部门简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "部门简介")
	
	private String intro;
	/**
	 * 部门状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "部门状态（0:禁用|1:可用）", allowableValues = "1,0")
	@NotBlank(message = "部门状态必填")
	
	private String status;

}
