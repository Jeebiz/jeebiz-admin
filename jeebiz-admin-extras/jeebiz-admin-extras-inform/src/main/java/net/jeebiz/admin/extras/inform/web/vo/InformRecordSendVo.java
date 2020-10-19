/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.inform.setup.InformProvider;

@ApiModel(value = "InformRecordSendVo", description = "消息通知发送对象")
@Getter
@Setter
@ToString
public class InformRecordSendVo {

	/**
	 * 消息通知接收人ID集合
	 */
	@ApiModelProperty(name = "toList", required = true, dataType = "java.util.List<String>", value = "消息通知接收人ID集合")
	@NotNull(message = "至少需要指定一个消息通知接收人")
	private List<String> toList;
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", required = true, dataType = "InformType", value = "发送该消息通知的提供者")
	@NotEmpty(message = "消息通知的提供者不能为空")
	private InformProvider provider;
	/**
	 * 消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）
	 */
	@ApiModelProperty(name = "tag", dataType = "String", value = "消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）")
	private String tag;
	/**
	 * 消息通知标题
	 */
	@ApiModelProperty(name = "title", required = true, dataType = "String", value = "消息通知标题")
	@NotBlank(message = "消息通知标题不能为空")
	private String title;
	/**
	 * 消息通知内容
	 */
	@ApiModelProperty(name = "content", required = true, dataType = "String", value = "消息通知内容")
	@NotBlank(message = "消息通知内容不能为空")
	private String content;
	/**
	 * 消息通知模板ID（系统内信息模板、微信订阅消息等模板ID）
	 */
	@ApiModelProperty(name = "tid", dataType = "String", value = "消息通知模板ID（系统内信息模板、微信订阅消息等模板ID）")
	private String tid;
	/**
	 * 通知信息关联数据载体,JOSN格式的数据
	 */
	@ApiModelProperty(name = "payload", dataType = "String", value = "通知信息关联数据载体,JOSN格式的数据")
	private String payload;
	
}
