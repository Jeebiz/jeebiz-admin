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

@ApiModel(value = "RolePaginationDTO", description = "角色信息分页查询参数DTO")
@Getter
@Setter
@ToString
public class RolePaginationDTO extends AbstractPaginationDTO {
	
	/**
	 * 角色名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "角色名称")
	private String name;
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

}
