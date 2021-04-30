/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

/**
 * 功能菜单-功能操作关系表
 */
@Alias(value = "AuthzRelationModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzRelationModel extends BaseModel<AuthzRelationModel> {
	
	/**
	 * 	主键 
	 */ 
	private String id;
	/**
	 * 功能菜单id
	 */
	private String mId;
	/**
	 * 功能操作代码
	 */
	private String code;
	/**
	 * 是否可见(1:可见|0:不可见)
	 */
	private String visible;
	/**
	 * 权限标记
	 */
	private String perms;
	/**
	 * 功能操作名称(用于覆盖功能操作表的操作名称)
	 */
	private String name;
	/**
	 * 显示顺序
	 */
	private String order;

}
