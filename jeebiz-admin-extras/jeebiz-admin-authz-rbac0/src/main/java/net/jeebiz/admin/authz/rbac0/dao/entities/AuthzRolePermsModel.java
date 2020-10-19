/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

@Alias(value = "AuthzRolePermsModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzRolePermsModel extends BaseModel<AuthzRolePermsModel> {

	/**
	 * 	主键 
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

}
