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
import net.jeebiz.boot.api.dao.entities.BaseModel;

@Alias(value = "AuthzUserAllotRoleModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzUserAllotRoleModel extends BaseModel<AuthzUserAllotRoleModel> {

	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 用户id集合
	 */
	private List<String> userIds;
 

}
