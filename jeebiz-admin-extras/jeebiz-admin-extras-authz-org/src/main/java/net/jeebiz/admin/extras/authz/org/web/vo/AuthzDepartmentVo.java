/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentVo", description = "部门信息Vo")
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
	@ApiModelProperty(name = "userId", dataType = "String", value = "部门创建人ID")
	private String userId;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime24() {
		return time24;
	}

	public void setTime24(String time24) {
		this.time24 = time24;
	}

}
