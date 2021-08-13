/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.rbac0.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "AuthzRolePaginationVo", description = "角色信息分页查询参数Vo")
public class AuthzRolePaginationVo extends AbstractPaginationDTO {
	
	/**
	 * 角色ID
	 */
	@ApiModelProperty(name = "id", dataType = "Integer", value = "角色ID")
	private String id;
	/**
	 * 角色类型（1:原生|2:继承|3:复制|4:自定义）
	 */
	@ApiModelProperty(name = "type", required = true, dataType = "String", value = "角色类型(1:原生|2:继承|3:复制|4:自定义)", allowableValues = "1,2,3,4")
	private String type;
	/**
	 * 角色状态(0:不可用|1:正常)
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "角色状态：0:不可用|1:正常", allowableValues = "1,0")
	private String status;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
