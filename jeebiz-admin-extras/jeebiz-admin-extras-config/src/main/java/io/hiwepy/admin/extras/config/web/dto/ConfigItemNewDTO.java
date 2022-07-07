/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.config.web.dto;

import io.hiwepy.admin.extras.config.utils.enums.ConfigItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "ConfigItemNewDTO", description = "配置信息项DTO")
@Data
public class ConfigItemNewDTO {

	/**
	 * 配置项ID
	 */
	@ApiModelProperty("配置项ID")
	private String id;
	/**
	 * 配置项分类
	 */
	@ApiModelProperty(value = "配置项分类")
	@NotNull(message = "配置项分类不能为空")
	private ConfigItemType configType;
	/**
	 * 配置项名称，如：AppKey
	 */
	@ApiModelProperty(value = "配置项名称，如：AppKey")
	@NotNull(message = "配置项名称不能为空")
	private String configKey;
	/**
	 * 配置项值，如：xxxxx12645645
	 */
	@ApiModelProperty(value = "配置项值，如：xxxxx12645645")
	@NotNull(message = "配置项值不能为空")
	private String configValue;

}
