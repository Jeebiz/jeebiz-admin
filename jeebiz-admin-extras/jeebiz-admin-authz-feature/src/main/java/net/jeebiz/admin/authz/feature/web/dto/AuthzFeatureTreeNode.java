/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.web.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApiModel(value = "AuthzFeatureTreeNode", description = "功能树节点信息")
@Getter
@Setter
@ToString
public class AuthzFeatureTreeNode implements Comparable<AuthzFeatureTreeNode>{

	/**
	 * 功能菜单或功能按钮id
	 */
	@ApiModelProperty(name = "id", value = "功能菜单或功能按钮id")
	private String id;
	/**
	 * 功能菜单或功能按钮名称
	 */
	@ApiModelProperty(name = "name", value = "功能菜单或功能按钮名称")
	private String name;
	/**
	 * 功能菜单或功能按钮显示标签
	 */
	@ApiModelProperty(name = "label", value = "功能菜单或功能按钮显示标签")
	private String label;
	/**
	 * 功能菜单或功能按钮简称
	 */
	@ApiModelProperty(name = "abb", value = "功能菜单或功能按钮简称")
	private String abb;
	/**
	 * 功能菜单或功能按钮编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据
	 */
	@ApiModelProperty(name = "code", value = "功能菜单或功能按钮编码：用于与功能操作代码组合出权限标记以及作为前段判断的依据")
	private String code;
	/**
	 * 功能菜单地址
	 */
	@ApiModelProperty(name = "path", value = "功能菜单地址")
	private String path;
	/**
	 * 功能组件路径
	 */
	@ApiModelProperty(name = "component", value = "功能组件路径")
	private String component;
	/**
	 * 菜单类型(1:原生|2:自定义)
	 */
	@ApiModelProperty(name = "type", value = "菜单类型(1:原生|2:自定义)")
	private String type;
	/**
	 * 图标样式或图标路径
	 */
	@ApiModelProperty(name = "icon", value = "图标样式或图标路径")
	private String icon;
	/**
	 * 显示顺序
	 */
	@ApiModelProperty(name = "order", value = "显示顺序")
	private String order;
	/**
	 * 父级节点id
	 */
	@ApiModelProperty(name = "pid", value = "父级节点id")
	private String pid;
	/**
	 * 权限标记
	 */
	@ApiModelProperty(name = "perms", value = "权限标记")
	private String perms;
	/**
	 * 是否授权(true:已授权|false:未授权)
	 */
	@ApiModelProperty(name = "checked", value = "是否授权(true:已授权|false:未授权)")
	private boolean checked;
	/**
	 * 是否根节点
	 */
	@ApiModelProperty(name = "root", value = "是否根节点")
	private boolean root;
	/**
	 * 是否叶子节点(true:是|false:否)
	 */
	@ApiModelProperty(name = "leaf", value = "是否叶子节点(true:是|false:否)")
	private boolean leaf;
	/**
	 * 子菜单或功能按钮
	 */
	@ApiModelProperty(name = "children", value = "子菜单或功能按钮")
	private List<AuthzFeatureTreeNode> children = Lists.newArrayList();

	@Override
	public int compareTo(AuthzFeatureTreeNode o) {
		try {
			return Integer.parseInt(StringUtils.trim(order)) - Integer.parseInt(StringUtils.trim(o.getOrder()));
		} catch (NumberFormatException e) {
		}
		return 0;
	}

}
