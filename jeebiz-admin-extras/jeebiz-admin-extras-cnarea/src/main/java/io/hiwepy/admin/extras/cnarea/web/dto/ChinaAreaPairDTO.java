/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.cnarea.web.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ChinaAreaPairDTO", description = "行政地区信息DTO")
@Data
public class ChinaAreaPairDTO implements Serializable {

	/**
	 * 区域名称
	 */
	@ApiModelProperty(name = "name", dataType = "String", value = "区域名称")
	private String name;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "区域编码")
	private String value;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(name = "children", dataType = "java.util.List<ChinaAreaPairDTO>", value = "区域子集")
	private List<ChinaAreaPairDTO> children;
	
}
