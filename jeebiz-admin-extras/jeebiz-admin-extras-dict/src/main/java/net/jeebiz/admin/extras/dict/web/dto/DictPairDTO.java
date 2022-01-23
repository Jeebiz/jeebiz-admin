/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.dict.web.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DictPairDTO", description = "基础数据传输对象")
@Data
public class DictPairDTO {

	/**
	 * 基础数据id编号
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "基础数据id编号")
	private String id;

	/**
	 * 基础数据字典Key
	 */
	@ApiModelProperty(name = "gkey", dataType = "String", value = "基础数据字典Key")
	private String gkey;

	/**
	 * 数据字典
	 */
	@ApiModelProperty(name = "gtext", dataType = "String", value = "数据字典")
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
	@ApiModelProperty(name = "orderBy", dataType = "String", value = "数据排序:组内排序", hidden = true)
	private int orderBy;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "createTime", value = "创建时间")
	@JsonFormat(pattern = DateFormats.DATE_LONGFORMAT)
	private LocalDateTime createTime;

}
