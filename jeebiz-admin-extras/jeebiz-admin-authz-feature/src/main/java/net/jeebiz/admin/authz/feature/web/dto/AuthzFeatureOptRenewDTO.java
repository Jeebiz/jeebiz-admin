/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzFeatureOptRenewDTO", description = "功能操作更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzFeatureOptRenewDTO implements Comparable<AuthzFeatureOptRenewDTO> {
	
	/**
	 * 功能菜单id
	 */
	@ApiModelProperty(name = "featureId", required = true, dataType = "String", value = "功能菜单id")
	@NotBlank(message = "功能菜单id必填")
	private String featureId;
	/**
	 * 功能操作id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "功能操作id")
	@NotBlank(message = "功能操作id必填")
	private String id;
	/**
	 * 功能操作名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "功能操作名称")
	@NotBlank(message = "功能操作名称必填")
	private String name;
	/**
	 * 功能操作图标样式
	 */
	@ApiModelProperty(name = "icon", required = true, dataType = "String", value = "功能操作图标样式")
	@NotBlank(message = "功能操作图标样式必填")
	private String icon;
	/**
	 * 功能操作排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "String", value = "功能操作排序")
	@NotBlank(message = "功能操作排序必填")
	private String order;
	/**
	 * 功能操作是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(name = "visible", required = true, dataType = "String", value = "功能操作是否可见(1:可见|0:不可见)", allowableValues = "1,0")
	@NotBlank(message = "功能操作是否可见必填")
	private String visible;
	/**
	 * 功能操作权限标记
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "String", value = "功能操作权限标记")
	@NotBlank(message = "功能操作权限标记必填")
	private String perms;
 
	@Override
	public int compareTo(AuthzFeatureOptRenewDTO o) {
		return Integer.parseInt(order) - Integer.parseInt(o.getOrder());
	}
	
}
