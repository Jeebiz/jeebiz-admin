/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "ChinaAreaPairVo", description = "行政地区信息vo")
@Getter
@Setter
@ToString
public class ChinaAreaPairVo {

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
	@ApiModelProperty(name = "children", dataType = "java.util.List<ChinaAreaPairVo>", value = "区域子集")
	private List<ChinaAreaPairVo> children;
	
}
