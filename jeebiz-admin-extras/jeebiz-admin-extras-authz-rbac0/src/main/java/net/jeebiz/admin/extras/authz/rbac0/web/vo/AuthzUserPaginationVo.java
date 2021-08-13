/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "AuthzUserPaginationVo", description = "用户信息分页查询参数Vo")
public class AuthzUserPaginationVo extends AbstractPaginationDTO {
	
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username", dataType = "String", value = "用户名")
	private String username;
	/**
	 * 用户状态(0:不可用|1:正常|2:锁定)
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "用户状态(0:不可用|1:正常|2:锁定)")
	private String status;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
