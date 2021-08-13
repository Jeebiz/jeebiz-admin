/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.google.common.collect.Lists;

import net.jeebiz.boot.api.dao.entities.BaseModel;

@Alias(value = "AuthzRolePermsModel")
@SuppressWarnings("serial")
public class AuthzRolePermsModel extends BaseModel<AuthzRolePermsModel> {

	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 角色授权的标记集合
	 */
	private List<String> perms = Lists.newArrayList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

}
