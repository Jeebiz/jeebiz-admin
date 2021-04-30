/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "AuthzTeamModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzTeamModel extends PaginationModel<AuthzTeamModel> {

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
	private String deptId;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 团队id编号
	 */
	private String id;
	/**
	 * 团队编码
	 */
	private String code;
	/**
	 * 团队名称
	 */
	private String name;
	/**
	 * 团队简介
	 */
	private String intro;
	/**
	 * 团队创建人id
	 */
	private String uid;
	/**
	 * 团队创建人名称
	 */
	private String uname;
	/**
	 * 团队状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 团队创建时间
	 */
	private String time24;

}
