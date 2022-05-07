/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;

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
	@TableField(value = "org_id")
	private String orgId;
	/**
	 * 机构名称
	 */
	@TableField(exist = false)
	private String orgName;
	/**
	 * 部门id编号
	 */
	@TableField(value = "dept_id")
	private String deptId;
	/**
	 * 部门名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 项目组id编号
	 */
	@TableField(value = "team_id")
	private String teamId;
	/**
	 * 部门名称
	 */
	@TableField(exist = false)
	private String teamName;
	/**
	 * 岗位id编号
	 */
	@TableField(value = "post_id")
	private String postId;
	/**
	 * 岗位名称
	 */
	@TableField(exist = false)
	private String postName;
	/**
	 * 员工id编号
	 */
	@TableId(value="staff_id",type= IdType.AUTO)
	private String id;
	/**
	 * 员工简介
	 */
	@TableField(value = "staff_intro")
	private String intro;
	/**
	 * 员工状态（0:禁用|1:可用）
	 */
	@TableField(value = "staff_status")
	private String status;
	/**
	 * 员工入职时间
	 */
	@TableField(value = "staff_hiredate")
	private String hiredate;
	/**
	 * 用户别名（昵称）
	 */
	@TableField(exist = false)
	private String nickname;
	/**
	 * 用户头像：图片路径或图标样式
	 */
	@TableField(exist = false)
	private String avatar;
	/**
	 * 手机号码
	 */
	@TableField(exist = false)
	private String phone;
	/**
	 * 电子邮箱
	 */
	@TableField(exist = false)
	private String email;

}
