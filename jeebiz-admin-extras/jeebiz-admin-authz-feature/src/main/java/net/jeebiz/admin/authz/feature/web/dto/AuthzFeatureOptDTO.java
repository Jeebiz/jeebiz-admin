/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.web.dto;
import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzFeatureOptDTO", description = "功能操作参数DTO")
@Getter
@Setter
@ToString
public class AuthzFeatureOptDTO implements Comparable<AuthzFeatureOptDTO> {
	
	/**
	 * 功能菜单id
	 */
	@ApiModelProperty(name = "featureId", dataType = "String", value = "功能菜单id")
	private String featureId;
	/**
	 * 父级功能菜单id
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级功能菜单id")
	private String parent;
	/**
	 * 功能操作id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "功能操作id")
	private String id;
	/**
	 * 功能操作名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "功能操作名称")
	private String name;
	/**
	 * 功能操作图标样式
	 */
	@ApiModelProperty(name = "icon", dataType = "String", value = "功能操作图标样式")
	private String icon;
	/**
	 * 功能操作排序
	 */
	@ApiModelProperty(name = "order", dataType = "String", value = "功能操作排序")
	private String order;
	/**
	 * 功能操作是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(name = "visible", dataType = "String", value = "功能操作是否可见(1:可见|0:不可见)", allowableValues = "1,0")
	private String visible;
	/**
	 * 功能操作是否授权(true:已授权|false:未授权)
	 */
	@ApiModelProperty(name = "checked", dataType = "Boolean", value = "功能操作是否授权(true:已授权|false:未授权)", allowableValues = "true,false")
	private boolean checked;
	/**
	 * 功能操作权限标记
	 */
	@ApiModelProperty(name = "perms", dataType = "String", value = "功能操作权限标记")
	private String perms;

	@Override
	public int compareTo(AuthzFeatureOptDTO o) {
		try {
			return Integer.parseInt(StringUtils.trim(order)) - Integer.parseInt(StringUtils.trim(o.getOrder()));
		} catch (NumberFormatException e) {
		}
		return 0;
	}
	
}
