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

@Alias(value = "AuthzStaffModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_org_staff")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzStaffModel extends PaginationEntity<AuthzStaffModel> {

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
	 * 项目组id编号
	 */
	private String teamId;
	/**
	 * 部门名称
	 */
	private String teamName;
	/**
	 * 岗位id编号
	 */
	private String postId;
	/**
	 * 岗位名称
	 */
	private String postName;
	/**
	 * 员工id编号
	 */
	private String id;
	/**
	 * 员工简介
	 */
	private String intro;
	/**
	 * 员工状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 员工入职时间
	 */
	private String time24;
	
	/**
	 * 用户别名（昵称）
	 */
	private String alias;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	private String avatar;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 电子邮箱
	 */
	private String email;

}
