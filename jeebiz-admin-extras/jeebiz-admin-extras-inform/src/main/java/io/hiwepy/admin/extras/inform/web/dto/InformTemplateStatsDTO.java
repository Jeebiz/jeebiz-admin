/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;


@ApiModel(value = "InformTemplateStatsDTO", description = "消息通知统计DTO")
@Data
public class InformTemplateStatsDTO {

	/**
	 * 消息通知模板类型
	 */
	@ApiModelProperty(value = "消息通知模板类型")
	private InformSendChannel channel;
	/**
	 * 消息通知模板已发消息总数
	 */
	@ApiModelProperty(value = "消息通知模板已发消息总数")
	private Integer total;
	/**
	 * 消息通知模板已发消息未读总数
	 */
	@ApiModelProperty(value = "消息通知模板已发消息未读总数")
	private Integer unread;

}
