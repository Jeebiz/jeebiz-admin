/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.hiwepy.admin.extras.inform.emums.InformTarget;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "InformTemplateDTO", description = "消息通知模板对象DTO")
@Getter
@Setter
@ToString
public class InformTemplateDTO {

	/**
	 * 消息通知模板ID
	 */
	@ApiModelProperty(value = "消息通知模板ID")
	private String id;
	/**
	 * 消息通知模板名称
	 */
	@ApiModelProperty(value = "消息模板名称")
	private String name;

	/**
	 * 消息通知模板类型
	 */
	@ApiModelProperty(value = "消息通知模板类型")
	private InformSendChannel channel;

	/**
	 * 消息通知标题（可能包含变量）
	 */
	@ApiModelProperty(value = "消息通知标题（可能包含变量）")
	private String title;

	/**
	 * 消息通知签名（短信消息模板需要使用）
	 */
	@ApiModelProperty(value = "消息通知签名（短信消息模板需要使用）")
	private String signature;

	/**
	 * 消息通知内容（可能包含变量）
	 */
	@ApiModelProperty(value = "消息通知内容（可能包含变量）")
	private String content;

	/**
	 * 模板消息通知对应第三方平台内的模板id
	 */
	@ApiModelProperty(value = "模板消息通知对应第三方平台内的模板id")
	private String templateId;

	/**
	 * 模板消息通知变量载体,JOSN格式的数据
	 */
	@ApiModelProperty(value = "模板消息通知变量载体,JOSN格式的数据")
	private String payload;
	/**
	 * 消息通知模板状态：（0:停用、1:启用）
	 */
	@ApiModelProperty(value = "消息通知模板状态：（0:停用、1:启用）", allowableValues="0,1", example = "1")
	private Integer status;
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
	/**
	 * 消息通知模板创建时间
	 */
	@ApiModelProperty(value = "消息通知创建时间")
	private String time24;

}
