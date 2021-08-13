/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzFeatureRenewVo", description = "功能菜单更新参数Vo")
public class AuthzFeatureRenewVo implements Comparable<AuthzFeatureRenewVo>{

	/**
	 * 功能菜单ID
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "功能菜单ID")
	@NotBlank(message = "功能菜单ID必填")
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAbb() {
		return abb;
	}

	public void setAbb(String abb) {
		this.abb = abb;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	@Override
	public int compareTo(AuthzFeatureRenewVo o) {
		return Integer.parseInt(order) - Integer.parseInt(o.getOrder());
	}

}
