/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentNewDTO", description = "新增部门信息参数DTO")
@Data
public class AuthzDepartmentNewDTO implements Serializable {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构id编号")
	@NotBlank(message = "机构id编号必填")
	
	private String orgId;
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
	 * 父级部门id编号
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级部门id编号")
	private String parent;

	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	}

}
