/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.web.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "KeyValueGroupRenewVo", description = "基础数据集合传输对象")
@Getter
@Setter
@ToString
public class KeyValueGroupRenewVo {

	/**
	 * 基础数据分组
	 */
	@ApiModelProperty(name = "gkey", dataType = "String", value = "基础数据分组")
	@NotBlank(message = "基础数据分组必填")
	private String gkey;
	
	@ApiModelProperty(name = "datas", dataType = "java.util.List<KeyValueRenewVo>", value = "批量更新的基础数据列表")
	private List<KeyValueRenewVo> datas;

}
