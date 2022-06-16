/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.web.dto;

import io.hiwepy.admin.extras.inform.emums.InformTarget;
import io.hiwepy.admin.extras.inform.emums.InformToType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "InformEventTargetDTO", description = "消息通知事件接收对象DTO")
@Data
public class InformEventTargetDTO {

	/**
	 * 消息通知事件ID
	 */
	@ApiModelProperty(value = "消息通知事件ID")
	@NotBlank(message = "消息通知事件ID不能为空")
	private String id;

	/**
	 * 消息通知事件通知对象：（ALL:全部、TRIGGER:触发人、SPECIFIC：部分对象）
	 */
	@ApiModelProperty(value = "消息通知事件通知对象：（ALL:全部、TRIGGER:触发人、SPECIFIC：部分对象）")
	@NotNull(message = "消息通知事件通知对象不能为空")
	private InformTarget target;

	/**
	 * 消息通知事件通知对象类型：（ORG:组织机构、ROLE:角色、POST：岗位、USER：人员）
	 */
	@ApiModelProperty(value = "消息通知事件通知对象类型：（ORG:组织机构、ROLE:角色、POST：岗位、USER：人员）")
	@NotNull(message = "消息通知事件通知对象类型不能为空")
	private InformToType toType;

	/**
	 * 消息通知事件通知对象ID
	 */
	@ApiModelProperty(value = "消息通知事件通知对象ID")
	@NotNull(message = "消息通知事件通知对象ID不能为空")
	private String targetId;

}
