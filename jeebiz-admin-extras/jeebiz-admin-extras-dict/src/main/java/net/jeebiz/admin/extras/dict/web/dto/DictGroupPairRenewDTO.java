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

@ApiModel(value = "DictGroupPairRenewDTO", description = "基础数据集合传输对象")
@Data
public class DictGroupPairRenewDTO {

	/**
	 * 数据字典
	 */
	@ApiModelProperty(name = "gkey", dataType = "String", value = "数据字典")
	@NotBlank(message = "基础数据字典必填")
	private String gkey;
	
	@ApiModelProperty(name = "datas", dataType = "java.util.List<KeyValueRenewDTO>", value = "批量更新的基础数据列表")
	private List<DictPairRenewDTO> datas;

}
