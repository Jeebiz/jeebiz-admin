/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.BaseModel;

/**
 * 功能菜单信息表
 */
@SuppressWarnings("serial")
@Alias(value = "AuthzFeatureModel")
@Getter
@Setter
@ToString
public class AuthzFeatureModel extends BaseModel<AuthzFeatureModel> {
	
	/**
	 * 功能菜单ID
	 */
	private String id;
	/**
	 * 功能菜单名称
	 */
	private String name;
	/**
	 * 功能菜单简称
	 */
	private String abb;
	/**
	 * 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
	 */
	private String code;
	/**
	 * 功能菜单URL
	 */
	private String url;
	/**
	 * 功能组件相对路径
	 */
	private String path;
	/**
	 * 菜单类型(1:原生|2:自定义)
	 */
	private String type;
	/**
	 * 菜单样式或菜单图标路径
	 */
	private String icon;
	/**
	 * 菜单显示顺序
	 */
	private String order;
	/**
	 * 父级功能菜单ID
	 */
	private String parent;
	/**
	 * 菜单是否可见(1:可见|0:不可见)
	 */
	private String visible;
	/**
	 * 菜单所拥有的权限标记
	 */
	private String perms;
	/**
	 * 菜单是否是根菜单
	 */
	private boolean root;
	/**
	 * 功能是否授权(1:已授权|0:未授权)
	 */
	private String checked;
}
