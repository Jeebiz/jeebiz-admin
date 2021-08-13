/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.authz.org.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "AuthzOrganizationPaginationVo", description = "机构信息分页查询参数")
public class AuthzOrganizationPaginationVo extends AbstractPaginationDTO {

	/**
	 * 机构名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "机构名称")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
