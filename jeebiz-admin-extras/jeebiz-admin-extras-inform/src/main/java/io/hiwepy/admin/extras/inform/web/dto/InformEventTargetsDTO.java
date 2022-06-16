/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.hiwepy.admin.extras.inform.emums.InformTarget;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "InformEventTargetsDTO", description = "消息通知事件接收对象DTO")
@Data
public class InformEventTargetsDTO {

	/**
	 * 消息通知事件ID
	 */
	@ApiModelProperty(value = "消息通知事件ID")
	@NotNull(message = "消息通知事件ID不能为空")
	private String eventId;

	/**
	 * 消息通知事件通知对象：（ALL:全部、TRIGGER:触发人、SPECIFIC：部分对象）
	 */
	@ApiModelProperty(value = "消息通知事件通知对象：（ALL:全部、TRIGGER:触发人、SPECIFIC：部分对象）")
	@NotNull(message = "消息通知事件通知对象不能为空")
	private InformTarget target;

	/**
	 * 消息通知事件通知对象集合
	 */
	@ApiModelProperty(value = "消息通知事件通知对象集合")
	@NotNull(message = "消息通知事件通知对象不能为空")
	private List<InformEventTargetDTO> targets;

}
