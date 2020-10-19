/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentVo", description = "部门信息Vo")
@Getter
@Setter
@ToString
public class AuthzDepartmentVo implements Serializable {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构ID编号")
	private String orgId;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "orgName", dataType = "String", value = "机构名称")
	private String orgName;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "部门ID编号")
	private String id;
	/**
	 * 部门编码
	 */
	@ApiModelProperty(name = "code", dataType = "String", value = "部门编码")
	private String code;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "部门名称")
	private String name;
	/**
	 * 部门简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "部门简介")
	private String intro;
	/**
	 * 父级部门ID编号
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级部门ID编号")
	private String parent;
	/**
	 * 部门创建人ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "部门创建人ID")
	private String uid;
	/**
	 * 部门状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "部门状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 部门创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "部门创建时间")
	private String time24;

	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	}
 
}
