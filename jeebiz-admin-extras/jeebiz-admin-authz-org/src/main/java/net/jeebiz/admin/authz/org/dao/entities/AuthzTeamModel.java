/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao.entities;

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
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "AuthzTeamModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_org_team")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzTeamModel extends PaginationEntity<AuthzTeamModel> {

	/**
	 * 机构id编号
	 */
	@TableField(value = "u_app_id")
	private String orgId;
	/**
	 * 机构名称
	 */
	@TableField(exist = false)
	private String orgName;
	/**
	 * 部门id编号
	 */
	@TableField(value = "u_app_id")
	private String deptId;
	/**
	 * 部门名称
	 */
	@TableField(exist = false)
	private String deptName;
	/**
	 * 团队id编号
	 */
	@TableId(value="id", type= IdType.AUTO)
	private String id;
	/**
	 * 团队编码
	 */
	@TableField(value = "u_app_channel")
	private String code;
	/**
	 * 团队名称
	 */
	@TableField(value = "u_app_channel")
	private String name;
	/**
	 * 团队简介
	 */
	@TableField(value = "u_app_channel")
	private String intro;
	/**
	 * 团队创建人id
	 */
	@TableField(value = "u_app_channel")
	private String uid;
	/**
	 * 团队创建人名称
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 团队状态（0:禁用|1:可用）
	 */
	@TableField(value = "u_app_channel")
	private String status;

}
