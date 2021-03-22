/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.web.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SettingsVo", description = "系统参数传输对象")
public class SettingsVo {

	/**
	 * 参数ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "参数ID编号")
	@NotBlank(message = "参数ID必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String id;
	
	/**
	 * 参数分组
	 */
	@ApiModelProperty(name = "gkey", required = true, dataType = "String", value = "参数分组")
	private String gkey;
	
	/**
	 * 参数标签
	 */
	@ApiModelProperty(name = "label", required = true, dataType = "String", value = "参数标签")
	private String label;
	/**
	 * 参数键
	 */
	@ApiModelProperty(name = "key", required = true, dataType = "String", value = "参数键")
	@NotBlank(message = "参数键必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String key;
	/**
	 * 参数值
	 */
	@ApiModelProperty(name = "text", required = true, dataType = "String", value = "参数值")
	@NotBlank(message = "参数值必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String value;
	/**
	 * 参数单位:如 KB
	 */
	@ApiModelProperty(name = "unit", dataType = "String", value = "参数单位:如 KB")
	private String unit;
	/**
	 * 参数展示类型：（text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url）
	 */
	@ApiModelProperty(name = "type", dataType = "String", value = "参数展示类型",
			allowableValues = "text,textarea,password,checkbox,radio,file,image,color,date,datetime,email,month,number,range,select,switch,tel,time,week,url")
	private String type;
	/**
	 * 参数验证规则：如（required|range:[0,100] (多个用|隔开)）
	 */
	@ApiModelProperty(name = "rules", dataType = "String", value = "参数验证规则：如（required|range:[0,100] (多个用|隔开)）")
	private String rules;
	/**
	 * 参数备注信息
	 */
	@ApiModelProperty(name = "remark", dataType = "String", value = "参数备注信息")
	private String remark;
	/**
	 * 参数提示信息
	 */
	@ApiModelProperty(name = "placeholder", dataType = "String", value = "参数提示信息")
	private String placeholder;
	/**
	 * 参数来源： 格式如 [{"key":"1","value":"选项一"},{"key":"2","value":"选项二"},...]
	 */
	@ApiModelProperty(name = "source", dataType = "String", value = "参数来源： 格式如 [{\"key\":\"1\",\"value\":\"选项一\"},{\"key\":\"2\",\"value\":\"选项二\"},...]")
	private String source;
	/**
	 * 参数状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "参数状态：0:不可用|1：可用", allowableValues = "0,1")
	@NotBlank(message = "参数状态必填")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String status;
	/**
	 * 参数排序:组内排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "String", value = "参数排序:组内排序")
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
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
