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
	 * 账号id
	 */
	private String id;
	/**
	 * 账号关联用户id
	 */
	private String uid;
	/**
	 * 账号关联用户code（短号/工号）
	 */
	protected String ucode;
	/**
	 * 登录方式（如：password：账号密码、weixin:微信登录...）
	 */
	protected String type;
	/**
	 * 账号名称
	 */
	private String username;
	/**
	 * 账号密码
	 */
	private String password;
	/**
	 * 账号密码盐：用于密码加解密
	 */
	private String salt;
	/**
	 * 账号秘钥：用于用户JWT加解密
	 */
	private String secret;
	/**
	 * 账号状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	private String status;
	/**
	 * 初始化时间
	 */
	private String time24;
	/**
	 * 账号角色
	 */
	private List<AuthzRoleEntity> roleList = Lists.newArrayList();


}
