/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.dao.entities;

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
import net.jeebiz.boot.api.dao.entities.BaseEntity;

@Alias(value = "AuthzThirdpartyUserModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_user_account")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzThirdpartyUserModel extends BaseEntity<AuthzThirdpartyUserModel> {

	/**
	 * 用户id
	 */
	@TableId(value="u_id",type= IdType.AUTO)
	private String id;
	/**
	 * 用户名
	 */
	@TableField(value = "u_account")
	private String account;
	/**
	 * 密码
	 */
	@TableField(value = "u_password")
	private String password;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	@TableField(value = "u_salt")
	private String salt;
	/**
	 * 用户秘钥：用于用户JWT加解密
	 */
	@TableField(value = "u_secret")
	private String secret;
	/**
	 * 用户唯一Uid（用户编号）
	 */
	@TableField(value = "u_uid")
	private String uuid;
	/**
	 * 用户唯一编号（内部工号）
	 */
	@TableField(value = "u_code")
	protected String ucode;
	/**
	 * 用户客户端应用id
	 */
	@TableField(value = "u_app_id")
	private String appId;
	/**
	 * 用户客户端应用渠道编码
	 */
	@TableField(value = "u_app_channel")
	private String appChannel;
	/**
	 * 用户客户端版本
	 */
	@TableField(value = "u_app_version")
	private String appVer;
	/**
	 * 用户是否在线（1：是，0：否）
	 */
	@TableField(value = "u_online")
	private String online;
	/**
	 * 用户最近一次登录时间
	 */
	@TableField(value = "u_latest_online")
	private String onlineLatest;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	@TableField(value = "u_status")
	private String status;
	/**
	 * 角色id（可能多个组合，如：1,2）
	 */
	@TableField(exist = false)
	private String roleId;
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	@TableField(exist = false)
	private String roleName;

}
