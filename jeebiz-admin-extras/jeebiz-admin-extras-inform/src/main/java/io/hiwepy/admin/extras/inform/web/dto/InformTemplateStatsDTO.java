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

@ApiModel(value = "InformTemplateStatsDTO", description = "消息通知统计DTO")
@Getter
@Setter
@ToString
public class InformTemplateStatsDTO {
	
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "channel", value = "发送该消息通知的提供者")
	private InformSendChannel channel;
	/**
	 * 消息通知模板已发消息总数
	 */
	@ApiModelProperty(name = "total", dataType = "Integer", value = "消息通知模板已发消息总数")
	private Integer total;
	/**
	 * 消息通知模板已发消息未读总数
	 */
	@ApiModelProperty(name = "unread", dataType = "Integer", value = "消息通知模板已发消息未读总数")
	private Integer unread;
	
}
