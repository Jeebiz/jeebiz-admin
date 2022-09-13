/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.feature.web.dto;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
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
	@ApiModelProperty(value = "功能菜单id")
	private String id;
	/**
	 * 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前端判断的依据
	 */
	@ApiModelProperty(value = " 功能菜单编码：用于与功能操作代码组合出权限标记以及作为前端判断的依据")
	private String code;
	/**
	 * 功能菜单名称
	 */
	@ApiModelProperty(value = "功能菜单名称")
	private String name;
	/**
	 * 功能菜单界面显示标签
	 */
	@ApiModelProperty(value = " 功能菜单界面显示标签")
	private String label;
	/**
	 * 功能菜单简称
	 */
	@ApiModelProperty(value = "功能菜单简称")
	private String abbr;
	/**
	 * 功能菜单路径
	 */
	@ApiModelProperty(value = "功能菜单路径")
	private String path;
	/**
	 * 功能组件路径
	 */
	@ApiModelProperty(value = "功能组件路径")
	private String component;
	/**
	 * 菜单类型(1:原生|2:自定义)
	 */
	@ApiModelProperty(value = "菜单类型(1:原生|2:自定义)")
	private String type;
	/**
	 * 菜单样式或菜单图标路径
	 */
	@ApiModelProperty(value = "菜单样式或菜单图标路径")
	private String icon;
	/**
	 * 菜单显示顺序
	 */
	@ApiModelProperty(value = "菜单显示顺序")
	private String orderBy;
	/**
	 * 父级功能菜单id
	 */
	@ApiModelProperty(value = "父级功能菜单id")
	private String parentId;
	/**
	 * 菜单是否可见(1:可见|0:不可见)
	 */
	@ApiModelProperty(value = "菜单是否可见(1:可见|0:不可见)")
	private String visible;
	/**
	 * 菜单所拥有的权限标记
	 */
	@ApiModelProperty(value = "菜单所拥有的权限标记")
	private String perms;
	/**
	 * 菜单是否授权(true:已授权|false:未授权)
	 */
	@ApiModelProperty(value = "菜单是否授权(true:已授权|false:未授权)")
	private boolean checked;
	/**
	 * 菜单是否是根菜单
	 */
	@ApiModelProperty(value = "菜单是否是根菜单")
	private boolean root;
	/**
	 * 菜单是叶子节点
	 */
	@ApiModelProperty(value = "菜单是叶子节点(true:是|false:否)")
	private boolean leaf;

	@Override
	public int compareTo(FeatureDTO o) {
		try {
			return Integer.parseInt(StringUtils.trim(orderBy)) - Integer.parseInt(StringUtils.trim(o.getOrderBy()));
		} catch (NumberFormatException e) {
		}
		return 0;
	}


}
