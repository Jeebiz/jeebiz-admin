/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.web.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;

@ApiModel(value = "InformRecordSendDTO", description = "消息通知发送对象")
@Data
public class InformRecordSendDTO {

	/**
	 * 消息通知发送通道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "消息通知发送通道")
	@NotNull(message = "消息通知发送通道不能为空")
	private InformSendChannel channel;
	/**
	 * 消息通知接收人ID集合
	 */
	@ApiModelProperty(required = true, value = "消息通知接收人ID集合")
	@NotNull(message = "至少需要指定一个消息通知接收人")
	private List<String> toList;
	/**
	 * 消息通知对应平台内的模板id
	 */
	@ApiModelProperty(value = "消息通知对应平台内的模板id")
	@NotBlank(message = "模板id不能为空")
	private String templateId;
	/**
	 * 消息通知标题
	 */
	@ApiModelProperty(required = true, value = "消息通知标题")
	@NotBlank(message = "消息通知标题不能为空")
	private String title;
	/**
	 * 消息通知内容
	 */
	@ApiModelProperty(required = true, value = "消息通知内容")
	@NotBlank(message = "消息通知内容不能为空")
	private String content;
	/**
	 * 消息通知变量载体,JOSN格式的数据
	 */
	@ApiModelProperty(value = "消息通知变量载体,JOSN格式的数据")
	private String payload;
	/**
	 * 消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）
	 */
	@ApiModelProperty(value = "消息通知内容中包含的路由跳转信息")
	private List<InformRecordRouteDTO> routes;

}
