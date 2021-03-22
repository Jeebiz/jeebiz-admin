/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "KeyValueRenewVo", description = "基础数据更新传输对象")
public class KeyValueRenewVo {

	/**
	 * 基础数据ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "基础数据ID编号")
	@NotBlank(message = "基础数据ID必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String id;

	/**
	 * 基础数据标签
	 */
	@ApiModelProperty(name = "label", required = true, dataType = "String", value = "基础数据标签")
	@NotBlank(message = "基础数据标签必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String label;
	/**
	 * 基础数据值
	 */
	@ApiModelProperty(name = "value", required = true, dataType = "String", value = "基础数据值")
	@NotBlank(message = "基础数据值必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String value;
	/**
	 * 基础数据状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", required = true, dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
	@NotBlank(message = "基础数据状态必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "String", value = "数据排序:组内排序", hidden = true)
	@NotNull(message = "基础数据排序必填")
	private int order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
