/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.web.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SettingsGroupRenewDTO", description = "系统参数集合传输对象")
@Data
public class SettingsGroupRenewDTO {

	/**
	 * 系统参数分组
	 */
	@ApiModelProperty(name = "gkey", required = true, dataType = "String", value = "系统参数分组")
	@NotBlank(message = "系统参数分组必填")
	
	private String gkey;
	
	@ApiModelProperty(name = "datas", required = true, dataType = "java.util.List<SettingsRenewDTO>", value = "批量更新的系统参数列表")
	private List<SettingsRenewDTO> datas;
	
}
