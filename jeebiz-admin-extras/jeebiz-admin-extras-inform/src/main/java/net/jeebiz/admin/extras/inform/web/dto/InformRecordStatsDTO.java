/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.inform.emums.InformSendChannel;

@ApiModel(value = "InformRecordStatsDTO", description = "我的消息通知统计DTO")
@Getter
@Setter
@ToString
public class InformRecordStatsDTO {
	 
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "channel", required = true, value = "发送该消息通知的提供者")
	private InformSendChannel channel;
	/**
	 * 该类型通知总数
	 */
	@ApiModelProperty(name = "total", dataType = "String", value = "该类型通知总数")
	private String total;
	/**
	 * 该类型未读通知总数
	 */
	@ApiModelProperty(name = "unread", dataType = "String", value = "该类型未读通知总数")
	private String unread;
	
}
