/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.web.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "AuthzOrganizationTreeDTO", description = "机构信息DTO")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class AuthzOrganizationTreeDTO implements Serializable {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "机构id编号")
	private String id;
	/**
	 * 机构编码
	 */
	@ApiModelProperty(name = "code", dataType = "String", value = "机构编码")
	private String code;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "机构名称")
	private String name;
	/**
	 * 机构简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "机构简介")
	private String intro;
	/**
	 * 父级机构id编号
	 */
	@ApiModelProperty(name = "parent", dataType = "String", value = "父级机构id编号")
	private String parent = "0";

	/**
	 * 菜单是叶子节点
	 */
	@ApiModelProperty(name = "leaf", dataType = "Boolean", value = "菜单是叶子节点(true:是|false:否)")
	private boolean leaf;
	/**
	 * 子菜单或功能按钮
	 */
	@ApiModelProperty(name = "children", dataType = "java.util.List<AuthzOrganizationTreeDTO>", value = "子菜单")
	private List<AuthzOrganizationTreeDTO> children = Lists.newArrayList();
	
	public String getParent() {
		return StringUtils.defaultString(parent, "0");
	}

}
