/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.hiwepy.admin.extras.inform.emums.InformTarget;
import io.hiwepy.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "InformTemplatePaginationDTO", description = "消息通知模板分页查询参数DTO")
@Getter
@Setter
@ToString
public class InformTemplatePaginationDTO extends AbstractPaginationDTO {

	/**
	 * 消息通知模板类型
	 */
	@ApiModelProperty(value = "消息通知模板类型")
	private InformSendChannel channel;
	/**
	 * 模糊搜索关键字
	 */
	@ApiModelProperty(name = "keywords", value = "模糊搜索关键字")
	private String keywords;

}
