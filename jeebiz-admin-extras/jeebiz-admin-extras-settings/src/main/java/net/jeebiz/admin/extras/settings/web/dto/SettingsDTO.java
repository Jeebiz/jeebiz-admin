/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.settings.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SettingsDTO", description = "系统参数传输对象")
@Data
public class SettingsDTO {

	/**
	 * 参数ID编号
	 */
	@ApiModelProperty(name = "id", required = true, dataType = "String", value = "参数ID编号")
	@NotBlank(message = "参数ID必填")
	
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
	
	private String key;
	/**
	 * 参数值
	 */
	@ApiModelProperty(name = "text", required = true, dataType = "String", value = "参数值")
	@NotBlank(message = "参数值必填")
	
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
	
	private String status;
	/**
	 * 参数排序:组内排序
	 */
	@ApiModelProperty(name = "order", required = true, dataType = "String", value = "参数排序:组内排序")
	private int order;

}
