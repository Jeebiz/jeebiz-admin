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
import net.jeebiz.admin.extras.inform.emums.InformTarget;
import net.jeebiz.boot.api.dto.AbstractPaginationDTO;

@ApiModel(value = "InformTemplatePaginationDTO", description = "消息通知模板分页查询参数DTO")
@Getter
@Setter
@ToString
public class InformTemplatePaginationDTO extends AbstractPaginationDTO {

	/**
	 * 消息通知推送对象
	 */
	@ApiModelProperty(name = "target", value = "消息通知推送对象")
	private InformTarget target;
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "channel", value = "发送该消息通知的提供者")
	private InformSendChannel channel;
	/**
	 * 模糊搜索关键字
	 */
	@ApiModelProperty(name = "keywords", value = "模糊搜索关键字")
	private String keywords;

}
