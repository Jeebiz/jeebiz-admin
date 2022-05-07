/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UserDTO", description = "用户信息参数DTO")
@Data
public class UserDTO {

	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "用户id")
	private String id;
	/**
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "用户唯一id（员工信息表id）")
	private String uid;
	/**
	 * 用户唯一编号（工号）
	 */
	@ApiModelProperty(name = "ucode", dataType = "String", value = "用户唯一编号（工号）")
	private String ucode;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "account", required = true, dataType = "String", value = "用户名")
	private String account;
	/**
	 * 用户密码
	 */
	@ApiModelProperty(name = "password", dataType = "String", value = "用户密码")
	private String password;
	/**
	 * 用户密码盐：用于密码加解密
	 */
	@ApiModelProperty(name = "salt", dataType = "String", value = "用户密码盐：用于密码加解密")
	private String salt;
	/**
	 * 用户秘钥：用于用户JWT加解密
	 */
	@ApiModelProperty(name = "secret", dataType = "String", value = "用户秘钥：用于用户JWT加解密")
	private String secret;
	/**
	 * 用户客户端应用id
	 */
	@ApiModelProperty(name = "appId", required = true, dataType = "String", value = "用户客户端应用id")
	private String appId;
	/**
	 * 用户客户端应用渠道编码
	 */
	@ApiModelProperty(name = "appChannel", required = true, dataType = "String", value = "用户客户端应用渠道编码")
	private String appChannel;
	/**
	 * 用户客户端版本
	 */
	@ApiModelProperty(name = "appVer", required = true, dataType = "String", value = "用户客户端应用版本号")
	private String appVer;

	/**
	 * 用户是否在线（1：是，0：否）
	 */
	@ApiModelProperty(name = "online", required = true, dataType = "String", value = "用户是否在线（1：是，0：否）")
	private String online;
	/**
	 * 用户最近一次登录时间
	 */
	@ApiModelProperty(name = "onlineLatest", required = true, dataType = "String", value = "用户最近一次登录时间")
	private String onlineLatest;
	
	/**
	 * 角色id（可能多个组合，如：1,2）
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色id（可能多个组合，如：1,2）")
	private String roleId;
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	@ApiModelProperty(name = "roleName", required = true, dataType = "String", value = "角色名称（可能多个组合，如：角色1,角色2）")
	private String roleName;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	@ApiModelProperty(name = "status", required = true, value = "用户状态（0:禁用|1:可用|2:锁定|3:密码过期）")
	private String status;
	/**
	 * 初始化时间
	 */
	@ApiModelProperty(name = "time24", required = true, dataType = "String", value = "初始化时间")
	@JsonFormat(pattern = DateFormats.DATE_LONGFORMAT)
	private LocalDateTime time24;
	/**
	 * 用户详情信息
	 */
	@ApiModelProperty(name = "profile", required = true, dataType = "UserProfileDTO", value = "用户详情信息")
	private UserProfileDTO profile;
	
}
