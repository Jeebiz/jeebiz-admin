/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzOrganizationVo", description = "机构信息Vo")
@SuppressWarnings("serial")
public class AuthzOrganizationVo implements Serializable {

	/**
	 * 机构ID编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "机构ID编号")
	private String id;
	/**
	 * 机构编码
	 */
	@ApiModelProperty(name = "code", dataType = "String", value = "机构编码")
	private String code;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "机构名称")
	private String name;
	/**
	 * 机构简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "机构简介")
	private String intro;
	/**
	 * 父级机构ID编号
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级机构ID编号")
	private String parent = "0";
	/**
	 * 机构创建人ID
	 */
	@ApiModelProperty(name = "userId", dataType = "String", value = "机构创建人ID")
	private String userId;
	/**
	 * 机构状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "机构状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 机构创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "机构创建时间")
	private String time24;

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
