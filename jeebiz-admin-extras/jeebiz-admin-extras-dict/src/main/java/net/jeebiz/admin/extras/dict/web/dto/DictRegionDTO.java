/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.dict.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DictRegionDTO", description = "国家地区编码传输对象")
@Data
public class DictRegionDTO {

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private String id;

	/**
	 * 两位字母
	 */
	@ApiModelProperty(value = "两位字母")
	private String code2;

	/**
	 * 三位字母
	 */
	@ApiModelProperty(value = "三位字母")
	private String code3;

	/**
	 * 数字
	 */
	@ApiModelProperty(value = "数字")
	private String number;

	/**
	 * ISO 3166-2相应代码
	 */
	@ApiModelProperty(value = "ISO 3166-2相应代码")
	private String isoCode;

	/**
	 * 国家或地区（ISO 英文用名）
	 */
	@ApiModelProperty(value = "国家或地区（ISO 英文用名）")
	private String isoName;

	/**
	 * 中国 惯用名
	 */
	@ApiModelProperty(value = "中国 惯用名")
	private String cnName;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

}
