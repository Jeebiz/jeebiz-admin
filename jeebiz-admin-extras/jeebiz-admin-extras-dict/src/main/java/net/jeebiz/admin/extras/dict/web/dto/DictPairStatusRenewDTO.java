/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DictPairStatusRenewDTO", description = "基础数据状态更新传输对象")
@Data
public class DictPairStatusRenewDTO {

	/**
	 * 基础数据id编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "基础数据id编号")
	@NotBlank(message = "基础数据id必填")
	private String id;
	/**
	 * 基础数据状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
	@NotBlank(message = "基础数据状态必填")
	private String status;

}
