/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.rbac0.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.vo.AbstractPaginationVo;

@ApiModel(value = "AuthzUserPaginationVo", description = "用户信息分页查询参数Vo")
@Getter
@Setter
@ToString
public class AuthzUserPaginationVo extends AbstractPaginationVo {

	/**
	 * 部门ID编号
	 */
	@ApiModelProperty(name = "deptId", dataType = "String", value = "部门ID编号")
	private String deptId;
	/**
	 * 项目组ID编号
	 */
	@ApiModelProperty(name = "teamId", dataType = "String", value = "项目组ID编号")
	private String teamId;
	 
	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "roleId", required = true, dataType = "String", value = "角色ID")
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
