/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao.entities;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "AuthzDepartmentModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_org_dept")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzDepartmentModel extends PaginationEntity<AuthzDepartmentModel> {

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
