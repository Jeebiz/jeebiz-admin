/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import hitool.core.collections.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.jeebiz.boot.api.dao.entities.PaginationEntity;
import net.jeebiz.boot.api.dao.entities.PairModel;

@Alias(value = "AuthzUserModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_user_list")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzUserEntity extends PaginationEntity<AuthzUserEntity> {

	/**
	 * 主键id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 账号名称
	 */
	@TableField(value = "username")
	private String username;
	/**
	 * 账号密码
	 */
	@TableField(value = "password")
	private String password;
	/**
	 * 账号密码盐：用于密码加解密
	 */
	@TableField(value = "salt")
	private String salt;
	/**
	 * 账号秘钥：用于用户JWT加解密
	 */
	@TableField(value = "secret")
	private String secret;
	/**
	 * 关联用户id
	 */
	@TableField(value = "`user_id`")
	private String userId;
	/**
	 * 关联用户code（短号/工号）
	 */
	@TableField(value = "user_code")
	protected String userCode;
	/**
	 * 登录方式（如：password：账号密码、weixin:微信登录...）
	 */
	@TableField(value = "`type`")
	private String type;
	/**
	 * 账号最近一次登录客户端应用id
	 */
	@TableField(value = "app_id")
	private String appId;
	/**
	 * 账号最近一次登录客户端应用渠道编码
	 */
	@TableField(value = "app_channel")
	private String appChannel;
	/**
	 * 账号最近一次登录客户端版本
	 */
	@TableField(value = "app_version")
	private String appVer;
	/**
	 * 用户是否在线（1：是，0：否）
	 */
	@TableField(value = "is_online")
	private boolean online;
	/**
	 * 用户最近一次登录时间
	 */
	@TableField(value = "latest_online")
	private LocalDateTime onlineLatest;
	/**
	 * 用户状态（0:禁用|1:可用|2:锁定|3:密码过期）
	 */
	@TableField(value = "`status`")
	private String status;
	/**
	 * 角色id（可能多个组合，如：1,2）
	 */
	@TableField(exist = false)
	private String roleId;
	@TableField(exist = false)
	private List<String> roles;
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	@TableField(exist = false)
	private String roleName;
	/**
	 * 关键词搜索
	 */
	@TableField(exist = false)
	private String keywords;
	/**
	 * 用户关联角色:这里这么写是为了提高前端渲染效率
	 */
	@TableField(exist = false)
	private List<PairModel> roleList;
	/**
	 * 用户详情信息
	 */
	@TableField(exist = false)
	private AuthzUserProfileEntity profile;

	public String getRoleId() {
		return StringUtils.defaultString(roleId, CollectionUtils.isEmpty(roleList) ? "" : roleList.stream().map(mapper -> mapper.getKey()).collect(Collectors.joining(",")));
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return StringUtils.defaultString(roleName, CollectionUtils.isEmpty(roleList) ? "" : roleList.stream().map(mapper -> mapper.getValue()).collect(Collectors.joining(",")));
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
