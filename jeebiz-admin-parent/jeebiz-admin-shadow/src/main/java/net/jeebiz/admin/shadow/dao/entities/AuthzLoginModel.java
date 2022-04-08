/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.shadow.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzRoleEntity;

@Alias(value = "AuthzLoginModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzLoginModel extends ShiroPrincipal {

	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 用户唯一Uid（用户编号）
	 */
	private String uid;
	/**
	 * 用户唯一编号（内部工号）
	 */
	protected String ucode;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	private String salt;
	/**
	 * 用户秘钥：用于用户JWT加解密
	 */
	private String secret;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	private String status;
	/**
	 * 初始化时间
	 */
	private String time24;
	/**
	 * 用户角色
	 */
	private List<AuthzRoleEntity> roleList = Lists.newArrayList();


}
