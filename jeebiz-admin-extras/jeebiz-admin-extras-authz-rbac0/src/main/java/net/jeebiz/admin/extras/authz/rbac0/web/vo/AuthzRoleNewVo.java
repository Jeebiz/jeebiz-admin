/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.vo;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzRoleNewVo", description = "新增角色信息参数Vo")
public class AuthzRoleNewVo {

	/**
	 * 角色名称
	 */
	@ApiModelProperty(name = "name", required = true, dataType = "String", value = "角色名称")
	private String name;
	/**
	 * 角色简介
	 */
	@ApiModelProperty(name = "intro", required = true, dataType = "String", value = "角色简介")
	private String intro;
	/**
	 * 角色授权的标记集合
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
	private List<String> perms = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

}
