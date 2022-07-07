/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.web.dto;

import io.hiwepy.admin.extras.config.utils.enums.ConfigType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "ConfigNewDTO", description = "新建配置信息DTO")
@Getter
@Setter
@ToString
public class ConfigNewDTO {

	/**
	 * 配置信息ID
	 */
	@ApiModelProperty(value = "配置信息ID")
	private String id;

	/**
	 *  钉钉机构 CropId
	 */
	@ApiModelProperty(value = "钉钉机构 CropId")
	private String corpId;

	/**
	 *  组织机构ID
	 */
	@ApiModelProperty(value = "组织机构ID")
	@NotNull(message = "组织机构ID不能为空")
	private Long deptId;

	/**
	 *  配置类型
	 */
	@ApiModelProperty(value = "配置类型")
	@NotNull(message = "配置类型不能为空")
	private ConfigType configType;

	/**
	 * 配置项集合
	 */
	@ApiModelProperty(value = "配置项集合")
	@NotNull(message = "配置项不能为空")
	private List<ConfigItemNewDTO> configList;
	
}
