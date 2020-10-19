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

@ApiModel(value = "InformRecordVo", description = "消息通知内容Vo")
@Getter
@Setter
@ToString
public class InformRecordVo {

	/**
	 * 消息通知ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "消息通知ID")
	private String id;
	/**
	 * 消息通知创建人ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "消息通知创建人ID")
	private String uid;
	/**
	 * 消息通知创建人
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "消息通板创建人")
	private String uname;
	/**
	 * 发送该消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", required = true, dataType = "InformType", value = "发送该消息通知的提供者")
	private InformProvider provider;
	/**
	 * 消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）
	 */
	@ApiModelProperty(name = "tag", dataType = "String", value = "消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）")
	private String tag;
	/**
	 * 消息通知类型：（1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）
	 */
	private String type;
	/**
	 * 消息通知接收人ID
	 */
	@ApiModelProperty(name = "toUid", dataType = "String", value = "消息通知接收人ID")
	private String toUid;
	/**
	 * 消息通知接收人工号
	 */
	@ApiModelProperty(name = "toUkey", dataType = "String", value = "消息通知接收人工号")
	private String toUkey;
	/**
	 * 消息通知接收人姓名
	 */
	@ApiModelProperty(name = "toUname", dataType = "String", value = "消息通知接收人姓名")
	private String toUname;
	/**
	 * 消息通知接手机号码
	 */
	@ApiModelProperty(name = "toPhone", dataType = "String", value = "消息通知接手机号码")
	private String toPhone;
	/**
	 * 消息通知接邮件
	 */
	@ApiModelProperty(name = "toEmail", dataType = "String", value = "消息通知接邮件")
	private String toEmail;
	/**
	 * 消息通知标题（可能包含变量）
	 */
	@ApiModelProperty(name = "title", dataType = "String", value = "消息通知标题（可能包含变量）")
	private String title;
	/**
	 * 消息通知内容（可能包含变量）
	 */
	@ApiModelProperty(name = "content", dataType = "String", value = "消息通知内容（可能包含变量）")
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
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "消息通知阅读状态：（0:未阅读、1:已阅读）")
	private String status;
	/**
	 * 消息通知创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "消息通知创建时间")
	private String time24;

}
