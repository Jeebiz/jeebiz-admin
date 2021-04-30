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

@Alias(value = "AuthzDepartmentModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzDepartmentModel extends PaginationModel<AuthzDepartmentModel> {

	/**
	 * 机构id编号
	 */
	private String orgId;
	/**
	 * 机构名称
	 */
	private String orgName;
	/**
	 * 部门id编号
	 */
	private String id;
	/**
	 * 部门编码
	 */
	private String code;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 部门说明
	 */
	private String intro;
	/**
	 * 父级部门id编号
	 */
	private String parent;
	/**
	 * 部门创建人id
	 */
	private String uid;
	/**
	 * 部门创建人名称
	 */
	private String uname;
	/**
	 * 部门状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 部门创建时间
	 */
	private String time24;
 
	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	} 

}
