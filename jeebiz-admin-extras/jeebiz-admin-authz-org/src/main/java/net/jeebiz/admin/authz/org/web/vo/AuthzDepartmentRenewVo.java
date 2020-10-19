/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.web.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentRenewVo", description = "部门信息更新参数Vo")
@Getter
@Setter
@ToString
public class AuthzDepartmentRenewVo implements Serializable {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构ID编号")
	@NotBlank(message = "机构ID编号必填")
	
	private String orgId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "部门ID编号")
	@NotBlank(message = "部门ID编号")
	
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
