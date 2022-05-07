/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.dict.web.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础数据字典信息Model
 */
@ApiModel(value = "DictGroupDTO", description = "基础数据字典信息")
@Data
public class DictGroupDTO {

	/**
	 * 数据字典id
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "数据字典id")
	private String id;
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
	 * 数据字典简介
	 */
	@ApiModelProperty(name = "intro", dataType = "String", value = "数据字典简介")
	private String intro;
	/**
	 * 数据字典状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "数据字典状态：0:不可用、1：可用")
	private String status;
	/**
	 * 数据字典排序
	 */
	@ApiModelProperty(name = "orderBy", dataType = "int", value = "数据字典排序")
	private int orderBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "createTime", value = "创建时间")
	@JsonFormat(pattern = DateFormats.DATE_LONGFORMAT)
	private LocalDateTime createTime;

}
