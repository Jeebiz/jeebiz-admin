/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础数据字典信息Model
 */
@ApiModel(value = "DictGroupNewDTO", description = "基础数据字典新增信息")
@Data
public class DictGroupNewDTO {

	/**
	 * 数据字典键
	 */
	@ApiModelProperty(name = "key", dataType = "String", value = "数据字典键")
	private String key;
	/**
	 * 数据字典值
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "数据字典值")
	private String value;
	/**
	 * 数据字典状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "数据字典状态：0:不可用、1：可用")
	private String status;
	/**
	 * 数据字典简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "数据字典简介")
	private String intro;
	/**
	 * 数据字典排序
	 */
	@ApiModelProperty(name = "orderBy", dataType = "int", value = "数据字典排序")
	private int orderBy;


}
