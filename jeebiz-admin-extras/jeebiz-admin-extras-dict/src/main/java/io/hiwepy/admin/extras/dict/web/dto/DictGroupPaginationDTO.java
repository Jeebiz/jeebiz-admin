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

@ApiModel(value = "DictGroupPaginationDTO", description = "基础数据字典分页查询参数DTO")
@Getter
@Setter
@ToString
public class DictGroupPaginationDTO extends AbstractPaginationDTO {
	
	/**
	 * 数据字典
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "数据字典")
	private String value;

}
