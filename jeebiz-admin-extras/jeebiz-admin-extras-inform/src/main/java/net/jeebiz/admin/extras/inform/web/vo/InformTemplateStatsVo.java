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

@ApiModel(value = "InformTemplateStatsVo", description = "消息通知统计Vo")
@Getter
@Setter
@ToString
public class InformTemplateStatsVo {
	
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", dataType = "InformType", value = "发送该消息通知的提供者")
	private InformProvider provider;
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
