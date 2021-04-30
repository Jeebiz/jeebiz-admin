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

@SuppressWarnings("serial")
@Alias("AuthzFeatureOptModel")
@Getter
@Setter
@ToString
public class AuthzFeatureOptModel extends BaseModel<AuthzFeatureOptModel> {

	/**
	 * 功能菜单id
	 */
	private String featureId;
	/**
	 * 功能操作id
	 */
	private String id;
	/**
	 * 功能操作名称
	 */
	private String name;
	/**
	 * 功能操作图标样式
	 */
	private String icon;
	/**
	 * 功能操作排序
	 */
	private String order;
	/**
	 * 功能操作是否可见(1:可见|0:不可见)
	 */
	private String visible;
	/**
	 * 功能操作是否授权(1:已授权|0:未授权)
	 */
	private String checked;
	/**
	 * 功能操作权限标记
	 */
	private String perms;
	
}
