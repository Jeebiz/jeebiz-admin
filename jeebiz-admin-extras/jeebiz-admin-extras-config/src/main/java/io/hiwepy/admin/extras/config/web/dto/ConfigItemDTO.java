/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.config.web.dto;

import io.hiwepy.admin.extras.config.utils.enums.ConfigItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ConfigItemDTO", description = "配置项DTO")
@Data
public class ConfigItemDTO {

	/**
	 * 配置项ID
	 */
	@ApiModelProperty("配置项ID")
	private String id;
	/**
	 * 所属配置信息ID
	 */
	@ApiModelProperty(value = "所属配置信息ID")
	private String configId;
	/**
	 * 配置项分类
	 */
	@ApiModelProperty(value = "配置项分类")
	private ConfigItemType configType;
	/**
	 * 配置项名称，如：AppKey
	 */
	@ApiModelProperty(value = "配置项名称，如：AppKey")
	private String configKey;
	/**
	 * 配置项值，如：xxxxx12645645
	 */
	@ApiModelProperty(value = "配置项值，如：xxxxx12645645")
	private String configValue;

}
