/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.feature.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "FeatureNewDTO", description = "新增功能菜单参数DTO")
@Getter
@Setter
@ToString
public class FeatureNewDTO implements Comparable<FeatureNewDTO> {

	/**
	 * 功能菜单名称
	 */
	@ApiModelProperty(required = true, value = "功能菜单名称")
	@NotBlank(message = "功能菜单名称必填")
	private String name;
	/**
	 * 功能菜单界面显示标签
	 */
	@ApiModelProperty(required = true, value = "功能菜单界面显示标签")
	@NotBlank(message = "功能菜单界面显示标签必填")
	private String label;
	/**
	 * 功能菜单简称
	 */
	@ApiModelProperty(required = true, value = "功能菜单简称")
	@NotBlank(message = "功能菜单简称必填")
	private String abbr;
	/**
	 * 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前端判断的依据
	 */
	@ApiModelProperty(required = true, value = "功能菜单编码：用于与功能操作代码组合出权限标记以及作为前端判断的依据")
	@NotBlank(message = "功能菜单编码必填")
	private String code;
	/**
	 * 功能菜单路径
	 */
	@ApiModelProperty(required = true, value = "功能菜单路径")
	@NotBlank(message = "功能菜单路径必填")
	private String path;
	/**
	 * 功能组件路径
	 */
	@ApiModelProperty(value = "功能组件路径")
	private String component;
	/**
	 * 菜单样式或菜单图标路径
	 */
	@ApiModelProperty(required = true, value = "菜单样式或菜单图标路径")
	private String icon;
	/**
	 * 菜单显示顺序
	 */
	@ApiModelProperty(required = true, value = "菜单显示顺序")
	@NotBlank(message = "菜单显示顺序必填")
	private String orderBy;
	/**
	 * 父级功能菜单id
	 */
	@ApiModelProperty(value = "父级功能菜单id")
	private String parentId;
	/**
	 * 菜单是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(required = true, value = "菜单是否可见(1:可见|0:不可见)", allowableValues = "1,2")
	@NotBlank(message = "菜单是否可见必填")
	private String visible;

	@Override
	public int compareTo(FeatureNewDTO o) {
		return Integer.parseInt(orderBy) - Integer.parseInt(o.getOrderBy());
	}

}
