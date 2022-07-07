/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.hiwepy.admin.extras.inform.emums.InformFromType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;

@ApiModel(value = "InformRecordDTO", description = "消息通知内容DTO")
@Data
public class InformRecordDTO {

	/**
	 * 消息通知ID
	 */
	@ApiModelProperty(value = "消息通知ID")
	private String id;
	/**
	 * 消息通知记录来源类型：（EVENT:消息通知事件、SEND:用户主动发送）
	 */
	@ApiModelProperty(value = "消息通知记录来源类型：（EVENT:消息通知事件、SEND:用户主动发送）")
	private InformFromType fromType;
	/**
	 * 消息通知发送人id/事件Id
	 */
	@ApiModelProperty(value = "消息通知发送人id/事件Id")
	private String fromId;
	/**
	 * 消息通知发送人
	 */
	@ApiModelProperty(value = "消息通知发送人")
	private String fromUname;
	/**
	 * 消息通知发送通道
	 */
	@ApiModelProperty(value = "消息通知发送通道")
	private InformSendChannel channel;
	/**
	 * 消息通知标题
	 */
	@ApiModelProperty(value = "消息通知标题")
	private String title;
	/**
	 * 消息通知签名（短信消息模板需要使用）
	 */
	@ApiModelProperty(value = "消息通知签名（短信消息模板需要使用）")
	private String signature;
	/**
	 * 消息通知内容
	 */
	@ApiModelProperty(value = "消息通知内容")
	private String content;
	/**
	 * 消息通知对应平台内的模板id
	 */
	@ApiModelProperty(value = "消息通知对应平台内的模板id")
	private String templateId;
	/**
	 * 消息通知变量载体,JOSN格式的数据
	 */
	@ApiModelProperty(value = "消息通知变量载体,JOSN格式的数据")
	private String payload;
	/**
	 * 消息通知接收人id
	 */
	@ApiModelProperty(value = "消息通知接收人id")
	private String receiverId;
	/**
	 * 消息通知接收人
	 */
	@ApiModelProperty(value = "消息通知接收人")
	private String receiverUname;
	/**
	 * 消息通知处理流水编号
	 */
	@ApiModelProperty(value = "消息通知处理流水编号")
	private String flowNo;
	/**
	 * 消息通知关联业务id
	 */
	@ApiModelProperty(value = "消息通知关联业务id")
	private String bizId;
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	@ApiModelProperty(value = "消息通知阅读状态：（0:未阅读、1:已阅读）")
	private Integer status;
	/**
	 * 消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）
	 */
	@ApiModelProperty(value = "消息通知内容中包含的路由跳转信息（JSON格式：[{\"name\":\"路由名称\",\"word\":\"路由文字\",\"link\":\"路由跳转地址\"}]）")
	private String route;
	/**
	 * 消息通知创建时间
	 */
	@ApiModelProperty(value = "消息通知创建时间")
	private String time24;

}
