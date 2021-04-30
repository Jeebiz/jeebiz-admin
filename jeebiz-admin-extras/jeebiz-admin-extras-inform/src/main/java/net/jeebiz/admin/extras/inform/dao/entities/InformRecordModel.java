/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@SuppressWarnings("serial")
@Alias(value = "InformRecordModel")
@Getter
@Setter
@ToString
public class InformRecordModel extends PaginationModel<InformRecordModel> {

	/**
	 * 消息通知记录id
	 */
	private String id;
	private List<String> ids;
	/**
	 * 消息通知发送人id
	 */
	private String uid;
	/**
	 * 消息通知发送人
	 */
	private String uname;	
	/**
	 * 消息通知的发送提供者
	 */
	private InformProvider provider;
	/**
	 * 消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）
	 */
	private String tag;
	/**
	 * 消息通知接收人id
	 */
	private String toUid;
	/**
	 * 消息通知接收人
	 */
	private String toUname;
	/**
	 * 消息通知标题（可能包含变量）
	 */
	private String title;
	/**
	 * 消息通知内容（可能包含变量）
	 */
	private String content;
	/**
	 * 消息通知关联业务id
	 */
	private String bid;
	/**
	 * 消息通知模板id（系统内信息模板、微信订阅消息等模板id）
	 */
	private String tid;
	/**
	 * 通知信息关联数据载体,JOSN格式的数据
	 */
	private String payload;
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	private String status;
	/**
	 * 消息通知创建时间
	 */
	private String time24;
	/**
	 * 模糊搜索关键字
	 */
	private String keywords;
	
}
