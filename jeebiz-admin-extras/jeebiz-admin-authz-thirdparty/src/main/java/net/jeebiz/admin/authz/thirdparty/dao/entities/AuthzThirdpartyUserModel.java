/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

@Alias(value = "AuthzThirdpartyUserModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzThirdpartyUserModel extends BaseModel<AuthzThirdpartyUserModel> {

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
	 * 用户客户端应用id
	 */
	private String appId;
	/**
	 * 用户客户端应用渠道编码
	 */
	private String appChannel;
	/**
	 * 用户客户端版本
	 */
	private String appVer;

	/**
	 * 用户是否在线（1：是，0：否）
	 */
	private String online;
	/**
	 * 用户最近一次登录时间
	 */
	private String onlineLatest;
	/**
	 * 初始化时间
	 */
	private String time24;
	
	/**
	 * 角色id（可能多个组合，如：1,2）
	 */
	private String roleId;
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	private String roleName;

}
