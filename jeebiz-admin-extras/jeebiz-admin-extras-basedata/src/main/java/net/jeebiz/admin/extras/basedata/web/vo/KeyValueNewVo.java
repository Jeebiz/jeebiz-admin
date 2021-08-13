/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "KeyValueNewVo", description = "新增基础数据参数Vo")
public class KeyValueNewVo {

	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(name = "gkey", required = true, dataType = "String", value = "基础数据分组")
	@NotBlank(message = "基础数据分组必填")
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
	 * 基础数据状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
	@NotBlank(message = "基础数据状态必填")
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "String", value = "数据排序:组内排序", hidden = true)
	@NotNull(message = "基础数据排序必填")
	private int order;

	public String getGkey() {
		return gkey;
	}

	public void setGkey(String gkey) {
		this.gkey = gkey;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
