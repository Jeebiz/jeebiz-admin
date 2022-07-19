/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.config.web.dto;

import io.hiwepy.admin.extras.config.utils.enums.ConfigType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "ConfigDTO", description = "配置信息DTO")
@Data
public class ConfigDTO {

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
	 *  钉钉机构密钥
	 */
	@ApiModelProperty(value = "钉钉机构密钥")
	private String corpSecret;

	/**
	 *  组织机构ID
	 */
	@ApiModelProperty(value = "组织机构ID")
	private Long deptId;

	/**
	 *  配置类型
	 */
	@ApiModelProperty(value = "配置类型")
	private ConfigType configType;

	/**
	 * 配置项集合
	 */
	@ApiModelProperty(value = "配置项集合")
	private List<ConfigItemDTO> configList;

}
