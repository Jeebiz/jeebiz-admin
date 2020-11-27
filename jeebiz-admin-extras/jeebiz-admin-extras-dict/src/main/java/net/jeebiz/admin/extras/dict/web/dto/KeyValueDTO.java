/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "KeyValueDTO", description = "基础数据传输对象")
@Data
public class KeyValueDTO {

	/**
	 * 基础数据ID编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "基础数据ID编号")
	private String id;
	
	/**
	 * 基础数据分组Key
	 */
	@ApiModelProperty(name = "gkey", dataType = "String", value = "基础数据分组Key")
	private String gkey;
	
	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(name = "gtext", dataType = "String", value = "基础数据分组")
	private String gtext;
	
	/**
	 * 基础数据标签
	 */
	@ApiModelProperty(name = "label", dataType = "String", value = "基础数据标签")
	private String label;
	/**
	 * 基础数据键
	 */
	@ApiModelProperty(name = "key", dataType = "String", value = "基础数据键")
	private String key;
	/**
	 * 基础数据值
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "基础数据值")
	private String value;
	/**
	 * 基础数据描述
	 */
	@ApiModelProperty(name = "text", dataType = "String", value = "基础数据描述")
	private String text;
	/**
	 * 基础数据状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	@ApiModelProperty(name = "order", dataType = "String", value = "数据排序:组内排序", hidden = true)
	private int order;
 

}
