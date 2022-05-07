/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "UserPaginationDTO", description = "用户信息分页查询参数DTO")
@Getter
@Setter
@ToString
public class UserPaginationDTO extends AbstractPaginationDTO {

	/**
	 * 部门id编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门id编号")
	private String deptId;
	/**
	 * 项目组id编号
	 */
	@ApiModelProperty(name = "teamId", dataType = "String", value = "项目组id编号")
	private String teamId;
	 
	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色id")
	private String roleId;
	/**
	 * 用户状态(0:不可用|1:正常|2:锁定)
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "用户状态(0:不可用|1:正常|2:锁定)")
	private String status;
	/**
	 * 关键词搜索
	 */
	@ApiModelProperty(name = "keywords", dataType = "String", value = "关键词")
	private String keywords;

}
