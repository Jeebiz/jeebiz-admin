/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "AuthzPostModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_org_post")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzPostModel extends PaginationEntity<AuthzPostModel> {

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
	 * 岗位id编号
	 */
	private String id;
	/**
	 * 岗位编码
	 */
	private String code;
	/**
	 * 岗位名称
	 */
	private String name;
	/**
	 * 岗位简介
	 */
	private String intro;
	/**
	 * 岗位创建人id
	 */
	private String uid;
	/**
	 * 岗位创建人名称
	 */
	private String uname;
	/**
	 * 岗位状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 岗位创建时间
	 */
	private String time24;

}
