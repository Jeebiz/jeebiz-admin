/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "DictRegionPaginationDTO", description = "国家地区编码分页查询参数DTO")
@Getter
@Setter
@ToString
public class DictRegionPaginationDTO extends AbstractPaginationDTO {

	/**
	 * 地区编码模糊查询关键字
	 */
	@ApiModelProperty(value = "地区编码模糊查询关键字")
	private String keywords;

}
