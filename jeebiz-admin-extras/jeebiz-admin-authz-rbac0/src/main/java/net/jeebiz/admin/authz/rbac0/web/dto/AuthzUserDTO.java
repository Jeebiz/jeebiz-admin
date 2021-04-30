/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzUserDTO", description = "用户信息参数DTO")
@Data
public class AuthzUserDTO {

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
	@ApiModelProperty(name = "username", required = true, dataType = "String", value = "用户名")
	private String username;
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
	 * 初始化时间
	 */
	@ApiModelProperty(name = "time24", required = true, dataType = "String", value = "初始化时间")
	private String time24;
	/**
	 * 用户详情信息
	 */
	@ApiModelProperty(name = "profile", required = true, dataType = "AuthzUserProfileDTO", value = "用户详情信息")
	private AuthzUserProfileDTO profile;
	
}
