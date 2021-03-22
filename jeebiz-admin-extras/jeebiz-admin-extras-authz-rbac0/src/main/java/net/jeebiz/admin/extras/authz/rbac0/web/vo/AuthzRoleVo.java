/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.vo;

import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AuthzRoleVo", description = "角色信息参数Vo")
public class AuthzRoleVo {

	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "id", dataType = "Integer", value = "角色ID")
	private String id;
	/**
	 * 角色编码
	 */
	@ApiModelProperty(name = "key", required = true, dataType = "String", value = "角色编码")
	private String key;
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
	 * 角色类型（1:原生|2:继承|3:复制|4:自定义）
	 */
	@ApiModelProperty(name = "type", required = true, dataType = "String", value = "角色类型(1:原生|2:继承|3:复制)", allowableValues = "1,2,3")
	private String type;
	/**
	 * 角色状态（0:禁用|1:可用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "角色状态（0:禁用|1:可用）")
	private String status;
	/**
	 * 角色授权的标记集合
	 */
	@ApiModelProperty(name = "perms", required = true, dataType = "java.util.List<String>", value = "角色授权的标记集合")
	private List<String> perms = Lists.newArrayList();
	/**
	 * 角色已分配用户量
	 */
	@ApiModelProperty(name = "users", dataType = "int", value = "角色已分配用户量")
	private int users;
	/**
	 * 初始化时间
	 */
	@ApiModelProperty(name = "time24",  dataType = "String", value = "初始化时间")
	private String time24;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

	public int getUsers() {
		return users;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public String getTime24() {
		return time24;
	}

	public void setTime24(String time24) {
		this.time24 = time24;
	}

}
