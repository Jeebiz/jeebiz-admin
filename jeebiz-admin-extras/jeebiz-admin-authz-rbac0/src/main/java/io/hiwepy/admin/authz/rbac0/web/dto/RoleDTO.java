/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "RoleDTO", description = "角色信息参数DTO")
@Getter
@Setter
@ToString
public class RoleDTO {

	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "id", dataType = "Integer", value = "角色id")
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
	@JsonFormat(pattern = DateFormats.DATE_LONGFORMAT)
	private LocalDateTime time24;

}
