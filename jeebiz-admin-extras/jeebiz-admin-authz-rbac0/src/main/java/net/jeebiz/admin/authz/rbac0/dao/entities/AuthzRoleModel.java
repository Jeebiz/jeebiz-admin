/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

@Alias(value = "AuthzRoleModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_role_list")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthzRoleModel extends PaginationEntity<AuthzRoleModel> {

	/**
	 * 角色id
	 */
	private String id;
	/**
	 * 角色编码
	 */
	private String key;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色简介
	 */
	private String intro;
	/**
	 * 角色类型（1:原生|2:继承|3:复制|4:自定义）
	 */
	private String type;
	/**
	 * 角色状态（0:禁用|1:可用）
	 */
	private String status;
	/**
	 * 角色授权的标记集合
	 */
	private List<String> perms = Lists.newArrayList();
	/**
	 * 角色已分配用户量
	 */
	private int users;
	/**
	 * 初始化时间
	 */
	private String time24;
	
}
