/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基础数据分组信息Model
 */
@ApiModel(value = "KeyGroupRenewVo", description = "基础数据分组修改信息")
@Getter
@Setter
@ToString
public class KeyGroupRenewVo {

	/**
	 * 数据分组ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "数据分组ID")
	private String id;
	/**
	 * 数据分组键
	 */
	@ApiModelProperty(name = "key", dataType = "String", value = "数据分组键")
	private String key;
	/**
	 * 数据分组值
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "数据分组值")
	private String value;
	/**
	 * 数据分组状态：0:不可用、1：可用
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "数据分组状态：0:不可用、1：可用")
	private String status;
	/**
	 * 数据分组排序
	 */
	@ApiModelProperty(name = "order", dataType = "int", value = "数据分组排序")
	private int order;
	 

}
