/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias(value = "AuthzRoleAllotUserModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzRoleAllotUserModel extends PaginationModel<AuthzRoleAllotUserModel> {
	/**
	 * 	主键 
	 */ 
	private String id;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 用户ID集合
	 */
	private List<String> userIds;
}
