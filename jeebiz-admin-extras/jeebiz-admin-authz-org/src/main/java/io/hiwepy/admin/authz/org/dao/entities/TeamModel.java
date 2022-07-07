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

@Alias(value = "TeamModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_org_team")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TeamModel extends PaginationEntity<TeamModel> {

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
	 * 团队id编号
	 */
	@TableId(value="team_id", type= IdType.AUTO)
	private String id;
	/**
	 * 团队名称
	 */
	@TableField(value = "team_name")
	private String name;
	/**
	 * 团队简介
	 */
	@TableField(value = "team_intro")
	private String intro;
	/**
	 * 团队创建人名称
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 团队状态（0:禁用|1:可用）
	 */
	@TableField(value = "team_status")
	private String status;

}
