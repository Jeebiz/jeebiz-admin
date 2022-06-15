/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;

import javax.validation.constraints.NotNull;

@ApiModel(value = "InformRecordStatsDTO", description = "我的消息通知统计DTO")
@Data
public class InformRecordStatsDTO {

	/**
	 * 消息通知发送通道
	 */
	@ApiModelProperty(name = "channel", value = "消息通知发送通道")
	private InformSendChannel channel;
	/**
	 * 该类型通知总数
	 */
	@ApiModelProperty(value = "该类型通知总数")
	private String total;
	/**
	 * 该类型未读通知总数
	 */
	@ApiModelProperty(value = "该类型未读通知总数")
	private String unread;
	
}
