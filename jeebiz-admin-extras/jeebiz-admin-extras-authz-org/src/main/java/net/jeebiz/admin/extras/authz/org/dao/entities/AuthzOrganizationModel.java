/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.dao.entities;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "AuthzOrganizationModel")
@SuppressWarnings("serial")
public class AuthzOrganizationModel extends PaginationModel {

	/**
	 * 机构ID编号
	 */
	private String id;
	/**
	 * 机构编码
	 */
	private String code;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 机构说明
	 */
	private String intro;
	/**
	 * 父级机构ID编号
	 */
	private String parent = "0";
	/**
	 * 机构创建人ID
	 */
	private String userId;
	/**
	 * 机构状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 机构创建时间
	 */
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
