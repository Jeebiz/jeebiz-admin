/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel(value = "AuthzDepartmentRenewVo", description = "部门信息更新参数Vo")
public class AuthzDepartmentRenewVo implements Serializable {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "orgId", required = true, dataType = "String", value = "机构ID编号")
	@NotBlank(message = "机构ID编号必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String orgId;
	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "部门ID编号")
	@NotBlank(message = "部门ID编号")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String id;
	/**
	 * 部门编码
	 */
	@ApiModelProperty(name = "code", required = true, dataType = "String", value = "部门编码")
	@NotBlank(message = "部门编码必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String code;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "部门名称")
	@NotBlank(message = "部门名称必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 部门简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "部门简介")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String intro;
	/**
	 * 部门状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "部门状态（0:禁用|1:可用）", allowableValues = "1,0")
	@NotBlank(message = "部门状态必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String status;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
