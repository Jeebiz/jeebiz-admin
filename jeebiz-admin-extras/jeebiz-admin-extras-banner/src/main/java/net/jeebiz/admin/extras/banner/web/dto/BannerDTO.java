package net.jeebiz.admin.extras.banner.web.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 我的应用信息
 */
@ApiModel(value = "BannerDTO", description = "我的应用信息DTO")
@Data
public class BannerDTO {

	/** 主键ID */
	private String id;
	/** 应用名称 */
	@ApiModelProperty(value = "name", required = true, dataType = "String", notes = "应用名称")
	@NotBlank(message = "应用名称必填")
	private String name;
	/** 应用描述 */
	@ApiModelProperty(value = "intro", required = true, dataType = "String", notes = "应用描述")
	@NotBlank(message = "应用描述必填")
	private String intro;
	/** 应用开发语言 */
	@ApiModelProperty(value = "lang", required = true, dataType = "String", notes = "应用开发语言")
	@NotBlank(message = "应用开发语言必填")
	private String lang;
	/** 应用部署地址 */
	@ApiModelProperty(value = "addr", required = true, dataType = "String", notes = "应用部署地址")
	@NotBlank(message = "应用部署地址必填")
	private String addr;

}
