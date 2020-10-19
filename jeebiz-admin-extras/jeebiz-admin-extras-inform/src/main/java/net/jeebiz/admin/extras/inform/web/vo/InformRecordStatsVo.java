/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.inform.setup.InformProvider;

@ApiModel(value = "InformRecordStatsVo", description = "我的消息通知统计Vo")
@Getter
@Setter
@ToString
public class InformRecordStatsVo {
	 
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", dataType = "InformType", value = "发送该消息通知的提供者")
	private InformProvider provider;
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
