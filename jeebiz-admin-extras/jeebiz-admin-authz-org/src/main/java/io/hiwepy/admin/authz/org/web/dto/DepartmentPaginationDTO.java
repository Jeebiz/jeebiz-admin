/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "DepartmentPaginationDTO", description = "部门信息分页查询参数")
@Getter
@Setter
@ToString
public class DepartmentPaginationDTO extends AbstractPaginationDTO {

	/**
	 * 部门名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "部门名称")
	
	private String name;
	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "orgId", dataType = "String", value = "机构id编号")
	private String orgId;
	
}
