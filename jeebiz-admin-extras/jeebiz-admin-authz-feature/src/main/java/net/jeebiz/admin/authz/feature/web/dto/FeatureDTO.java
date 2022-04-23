/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.web.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "FeatureDTO", description = "功能信息DTO")
@Getter
@Setter
@ToString
public class FeatureDTO implements Comparable<FeatureDTO>{

	/**
	 * 功能菜单id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "功能菜单id")
	private String id;
	/**
	 * 功能菜单名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "功能菜单名称")
	private String name;
	/**
	 * 功能菜单界面显示标签
	 */
	@ApiModelProperty(name = "label", dataType = "String", value = " 功能菜单界面显示标签")
	private String label;
	/**
	 * 功能菜单简称
	 */
	@ApiModelProperty(name = "abb", dataType = "String", value = "功能菜单简称")
	private String abb;
	/**
	 * 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
	 */
	@ApiModelProperty(name = "code", dataType = "String", value = " 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据")
	private String code;
	/**
	 * 功能菜单URL
	 */
	@ApiModelProperty(name = "url", dataType = "String", value = "功能菜单URL")
	private String url;
	/**
	 * 功能组件路径
	 */
	@ApiModelProperty(name = "path", required = true,dataType = "String", value = "功能组件路径")
	private String path;
	/**
	 * 菜单类型(1:原生|2:自定义)
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "菜单类型(1:原生|2:自定义)")
	private String type;
	/**
	 * 菜单样式或菜单图标路径
	 */
	@ApiModelProperty(name = "icon", dataType = "String", value = "菜单样式或菜单图标路径")
	private String icon;
	/**
	 * 菜单显示顺序
	 */
	@ApiModelProperty(name = "order", dataType = "String", value = "菜单显示顺序")
	private String order;
	/**
	 * 父级功能菜单id
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级功能菜单id")
	private String parent;
	@ApiModelProperty(name = "pid", dataType = "String", value = "父级功能菜单id")
	private String pid;
	/**
	 * 菜单是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(name = "visible", dataType = "String", value = "菜单是否可见(1:可见|0:不可见)")
	private String visible;
	/**
	 * 菜单所拥有的权限标记
	 */
	@ApiModelProperty(name = "icon", dataType = "String", value = "菜单样式或菜单图标路径")
	private String perms;
	/**
	 * 菜单是否授权(true:已授权|false:未授权)
	 */
	@ApiModelProperty(name = "checked", dataType = "Boolean", value = "菜单是否授权(true:已授权|false:未授权)")
	private boolean checked;
	/**
	 * 菜单是否是根菜单
	 */
	@ApiModelProperty(name = "root", dataType = "Boolean", value = "菜单是否是根菜单")
	private boolean root;
	/**
	 * 菜单是叶子节点
	 */
	@ApiModelProperty(name = "leaf", dataType = "Boolean", value = "菜单是叶子节点(true:是|false:否)")
	private boolean leaf;
	/**
	 * 子菜单或功能按钮
	 */
	@ApiModelProperty(name = "children", dataType = "java.util.List<FeatureDTO>", value = "子菜单")
	private List<FeatureDTO> children = Lists.newArrayList();
	/**
	 * 功能按钮
	 */
	@ApiModelProperty(name = "opts", dataType = "java.util.List<FeatureOptDTO>", value = "功能按钮：没有子菜单时该数据才有值")
	private List<FeatureOptDTO> opts = Lists.newArrayList();

	@Override
	public int compareTo(FeatureDTO o) {
		try {
			return Integer.parseInt(StringUtils.trim(order)) - Integer.parseInt(StringUtils.trim(o.getOrder()));
		} catch (NumberFormatException e) {
		}
		return 0;
	}


}
