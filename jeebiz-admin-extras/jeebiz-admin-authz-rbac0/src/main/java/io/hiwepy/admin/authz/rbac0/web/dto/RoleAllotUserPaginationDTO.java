/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.rbac0.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.boot.api.dto.AbstractOrderedPaginationDTO;

@ApiModel(value = "RoleAllotUserPaginationDTO", description = "角色分配用户分页查询参数DTO")
@Getter
@Setter
@ToString
public class RoleAllotUserPaginationDTO extends AbstractOrderedPaginationDTO {

	/**
	 * 角色id
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "角色id")
	@NotBlank(message = "角色id必填")
	private String id;
	/**
	 * 启用状态
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "启用状态", allowableValues = "0,1")
	private String status;

}
