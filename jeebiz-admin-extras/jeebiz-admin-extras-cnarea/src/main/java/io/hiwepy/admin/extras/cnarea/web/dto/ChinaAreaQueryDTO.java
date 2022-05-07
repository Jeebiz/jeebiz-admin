/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.cnarea.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ChinaAreaQueryDTO", description = "行政地区信息查询DTO")
@Data
public class ChinaAreaQueryDTO {

	/**
	 * 简称
	 */
	@ApiModelProperty(name = "sname", dataType = "String", value = "简称")
	private String sname;
	
}
