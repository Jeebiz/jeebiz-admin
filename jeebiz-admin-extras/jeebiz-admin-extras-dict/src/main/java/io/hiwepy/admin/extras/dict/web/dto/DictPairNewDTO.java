/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.dict.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DictPairNewDTO", description = "新增基础数据参数DTO")
@Data
public class DictPairNewDTO {

	/**
	 * 数据字典
	 */
	@ApiModelProperty(name = "gkey", required = true, dataType = "String", value = "数据字典")
	@NotBlank(message = "基础数据字典必填")
	private String gkey;
	/**
	 * 基础数据键
	 */
	@ApiModelProperty(name = "key", dataType = "String", value = "基础数据键")
	@NotBlank(message = "基础数据键必填")
	private String key;
	/**
	 * 基础数据标签
	 */
	@ApiModelProperty(name = "label", required = true, dataType = "String", value = "基础数据标签")
	@NotBlank(message = "基础数据标签必填")
	private String label;
	/**
	 * 基础数据值
	 */
	@ApiModelProperty(name = "value", required = true, dataType = "String", value = "基础数据值")
	@NotBlank(message = "基础数据值必填")
	private String value;
	/**
	 * 基础数据描述
	 */
	@ApiModelProperty(name = "text", dataType = "String", value = "基础数据描述")
	private String text;
	/**
	 * 基础数据状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
	@NotBlank(message = "基础数据状态必填")
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	@ApiModelProperty(name = "orderBy", required = true, dataType = "String", value = "数据排序:组内排序", hidden = true)
	@NotNull(message = "基础数据排序必填")
	private int orderBy;

}
