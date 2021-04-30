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

@ApiModel(value = "AuthzFeatureRenewDTO", description = "功能菜单更新参数DTO")
@Getter
@Setter
@ToString
public class AuthzFeatureRenewDTO implements Comparable<AuthzFeatureRenewDTO>{

	/**
	 * 功能菜单id
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "功能菜单id")
	@NotBlank(message = "功能菜单id必填")
	private String id;
	/**
	 * 功能菜单名称
	 */
	@ApiModelProperty(name = "name", required = true,dataType = "String", value = "功能菜单名称")
	@NotBlank(message = "功能菜单名称必填")
	private String name;
	/**
	 * 功能菜单界面显示标签
	 */
	@ApiModelProperty(name = "label", required = true,dataType = "String", value = "功能菜单界面显示标签")
	@NotBlank(message = "功能菜单界面显示标签必填")
	private String label;
	/**
	 * 功能菜单简称
	 */
	@ApiModelProperty(name = "abb", required = true,dataType = "String", value = "功能菜单简称")
	@NotBlank(message = "功能菜单简称必填")
	private String abb;
	/**
	 * 功能菜单URL
	 */
	@ApiModelProperty(name = "url", required = true,dataType = "String", value = "功能菜单URL")
	@NotBlank(message = "功能菜单URL必填")
	private String url;
	/**
	 * 功能菜单对应页面相对路径
	 */
	@ApiModelProperty(name = "path", required = true, dataType = "String", value = "功能菜单对应页面相对路径")
	@NotBlank(message = "功能菜单对应页面相对路径必填")
	private String path;
	/**
	 * 菜单样式或菜单图标路径
	 */
	@ApiModelProperty(name = "icon", dataType = "String", value = "菜单样式或菜单图标路径")
	private String icon;
	/**
	 * 菜单显示顺序
	 */
	@ApiModelProperty(name = "order", required = true,dataType = "String", value = "菜单显示顺序")
	@NotBlank(message = "菜单显示顺序必填")
	private String order;
	/**
	 * 菜单是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(name = "visible", required = true, dataType = "String", value = "菜单是否可见(1:可见|0:不可见)", allowableValues = "1,2")
	@NotBlank(message = "菜单是否可见必填")
	private String visible;

	@Override
	public int compareTo(AuthzFeatureRenewDTO o) {
		return Integer.parseInt(order) - Integer.parseInt(o.getOrder());
	}

}
