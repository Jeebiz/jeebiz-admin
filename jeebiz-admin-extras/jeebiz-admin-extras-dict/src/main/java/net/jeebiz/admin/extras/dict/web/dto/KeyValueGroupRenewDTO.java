/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "KeyValueGroupRenewDTO", description = "基础数据集合传输对象")
@Data
public class KeyValueGroupRenewDTO {

	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(name = "gkey", dataType = "String", value = "基础数据分组")
	@NotBlank(message = "基础数据分组必填")
	private String gkey;
	
	@ApiModelProperty(name = "datas", dataType = "java.util.List<KeyValueRenewDTO>", value = "批量更新的基础数据列表")
	private List<KeyValueRenewDTO> datas;

}
