/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.feature.web.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzFeatureOptRenewVo", description = "功能操作更新参数Vo")
public class AuthzFeatureOptRenewVo implements Comparable<AuthzFeatureOptRenewVo> {
	
	/**
	 * 功能菜单ID
	 */
	@ApiModelProperty(name = "featureId", required = true, dataType = "String", value = "功能菜单ID")
	@NotBlank(message = "功能菜单ID必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String featureId;
	/**
	 * 功能操作ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "功能操作ID")
	@NotBlank(message = "功能操作ID必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String id;
	/**
	 * 功能操作名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "功能操作名称")
	@NotBlank(message = "功能操作名称必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	/**
	 * 功能操作图标样式
	 */
	@ApiModelProperty(name = "icon", required = true, dataType = "String", value = "功能操作图标样式")
	@NotBlank(message = "功能操作图标样式必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String icon;
	/**
	 * 功能操作排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "String", value = "功能操作排序")
	@NotBlank(message = "功能操作排序必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String order;
	/**
	 * 功能操作是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(name = "visible", required = true, dataType = "String", value = "功能操作是否可见(1:可见|0:不可见)", allowableValues = "1,0")
	@NotBlank(message = "功能操作是否可见必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String visible;
	/**
	 * 功能操作权限标记
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "String", value = "功能操作权限标记")
	@NotBlank(message = "功能操作权限标记必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String perms;

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

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
	
	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	@Override
	public int compareTo(AuthzFeatureOptRenewVo o) {
		return Integer.parseInt(order) - Integer.parseInt(o.getOrder());
	}
	
}
