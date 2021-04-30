/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao.entities;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "AuthzOrganizationModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzOrganizationModel extends PaginationModel<AuthzOrganizationModel> {

	/**
	 * 机构id编号
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
	 * 父级机构id编号
	 */
	private String parent = "0";
	/**
	 * 机构创建人id
	 */
	private String uid;
	/**
	 * 机构创建人名称
	 */
	private String uname;
	/**
	 * 机构状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 机构创建时间
	 */
	private String time24;
 
	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	}

}
