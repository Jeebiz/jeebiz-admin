/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.PaginationModel;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.utils.CollectionUtils;

@Alias(value = "AuthzUserModel")
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthzUserModel extends PaginationModel<AuthzUserModel> {

	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 用户唯一UID（用户编号）
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
	 * 用户客户端应用ID
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
	 * 角色ID（可能多个组合，如：1,2）
	 */
	private String roleId;
	private List<String> roles = Lists.newArrayList();
	/**
	 * 角色名称（可能多个组合，如：角色1,角色2）
	 */
	private String roleName;
	/**
	 * 关键词搜索
	 */
	private String keywords;
	/**
	 * 用户关联角色:这里这么写是为了提高前端渲染效率
	 */
	private List<PairModel> roleList = Lists.newArrayList();
	/**
	 * 用户详情信息
	 */
	private AuthzUserProfileModel profile;

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
