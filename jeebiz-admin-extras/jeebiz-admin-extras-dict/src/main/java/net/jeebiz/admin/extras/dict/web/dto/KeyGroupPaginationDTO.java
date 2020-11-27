/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "KeyGroupPaginationDTO", description = "基础数据分组分页查询参数DTO")
@Getter
@Setter
@ToString
public class KeyGroupPaginationDTO extends AbstractPaginationDTO {
	
	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(name = "value", dataType = "String", value = "基础数据分组")
	private String value;

}
