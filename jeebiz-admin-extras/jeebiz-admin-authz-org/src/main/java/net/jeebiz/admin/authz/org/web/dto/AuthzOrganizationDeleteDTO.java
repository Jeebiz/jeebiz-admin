/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "AuthzOrganizationDeleteDTO", description = "机构删除参数DTO")
@SuppressWarnings("serial")
@Data
public class AuthzOrganizationDeleteDTO {

	/**
	 * 机构id编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "机构id编号")
	@NotBlank(message = "机构id编号必填")
	private String id;

}
