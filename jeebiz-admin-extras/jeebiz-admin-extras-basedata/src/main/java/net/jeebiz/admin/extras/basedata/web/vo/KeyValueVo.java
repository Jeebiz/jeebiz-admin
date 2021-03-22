/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "KeyValueVo", description = "基础数据传输对象")
public class KeyValueVo {

	/**
	 * 基础数据ID编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "基础数据ID编号")
	private String id;
	
	/**
	 * 基础数据分组Key
	 */
	@ApiModelProperty(name = "gkey", dataType = "String", value = "基础数据分组Key")
	@NotBlank(message = "基础数据分组必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
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
	@NotBlank(message = "基础数据标签必填")
	private String label;
	/**
	 * 基础数据键
	 */
	@ApiModelProperty(name = "key", dataType = "String", value = "基础数据键")
	@NotBlank(message = "基础数据键必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String key;
	/**
	 * 基础数据值
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "基础数据值")
	@NotBlank(message = "基础数据值必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String value;
	/**
	 * 基础数据状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "数据状态：0:不可用|1：可用", allowableValues = "0,1")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String status;
	/**
	 * 数据排序:组内排序
	 */
	@ApiModelProperty(name = "order", dataType = "String", value = "数据排序:组内排序", hidden = true)
	private int order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getGkey() {
		return gkey;
	}

	public void setGkey(String gkey) {
		this.gkey = gkey;
	}

	public String getGtext() {
		return gtext;
	}

	public void setGtext(String gtext) {
		this.gtext = gtext;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
