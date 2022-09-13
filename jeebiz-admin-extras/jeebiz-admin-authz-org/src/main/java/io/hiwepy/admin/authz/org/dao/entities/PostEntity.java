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

@Alias(value = "PostEntity")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_org_post")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PostEntity extends PaginationEntity<PostEntity> {

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
	 * 岗位id编号
	 */
	@TableId(value="post_id",type= IdType.AUTO)
	private String id;
	/**
	 * 岗位编码
	 */
	@TableField(value = "post_code")
	private String code;
	/**
	 * 岗位名称
	 */
	@TableField(value = "post_name")
	private String name;
	/**
	 * 岗位简介
	 */
	@TableField(value = "post_intro")
	private String intro;
	/**
	 * 岗位创建人名称
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 岗位状态（0:禁用|1:可用）
	 */
	@TableField(value = "post_status")
	private String status;

}
