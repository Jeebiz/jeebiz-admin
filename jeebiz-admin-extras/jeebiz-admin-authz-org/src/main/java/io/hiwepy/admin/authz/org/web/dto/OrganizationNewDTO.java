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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "OrganizationNewDTO", description = "新增机构信息参数DTO")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class OrganizationNewDTO implements Serializable {

	/**
	 * 机构编码
	 */
	@ApiModelProperty(name = "code", required = true, dataType = "String", value = "机构编码")
	@NotBlank(message = "机构编码必填")
	
	private String code;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "机构名称")
	@NotBlank(message = "机构名称必填")
	
	private String name;
	/**
	 * 机构简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "机构简介")
	
	private String intro;
	/**
	 * 父级机构id编号
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级机构id编号")
	private String parent = "0";
	/**
	 * 机构状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "机构状态（0:禁用|1:可用）", allowableValues = "1,0")
	@NotBlank(message = "机构状态必填")
	private String status;
 
	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	}
 

}
