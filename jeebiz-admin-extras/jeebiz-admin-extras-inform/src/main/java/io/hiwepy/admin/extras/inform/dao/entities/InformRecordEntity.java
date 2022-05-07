/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.dao.entities;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;

@SuppressWarnings("serial")
@Alias(value = "InformRecordEntity")
@TableName(value = "inform_records", keepGlobalPrefix = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InformRecordEntity extends PaginationEntity<InformRecordEntity> {

	/**
	 * 消息通知记录id
	 */
	@TableId(value="r_id",type= IdType.AUTO)
	private String id;
	/**
	 * 客户端应用ID
	 */
	@TableField(value = "app_id")
	private String appId;
	/**
	 * 客户端应用渠道
	 */
	@TableField(value = "app_channel")
	private String appChannel;
	/**
	 * 客户端版本
	 */
	@TableField(value = "app_version")
	private String appVer;
	/**
	 * 推送渠道
	 */
	@TableField(value = "send_channel")
	private InformSendChannel channel;
	/**
	 * 流水编号
	 */
	@TableField(value = "flow_no")
	private String flowNo;

	@TableField(exist = false)
	private List<String> ids;
	/**
	 * 消息通知发送人id
	 */
	@TableField(value = "r_uid")
	private String userId;
	/**
	 * 消息通知发送人
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 消息通知标签（自定义的通知标签，用于判断逻辑，如：1：信息通知、2：发起审批、3：审批通过、4：审批拒绝）
	 */
	@TableField(value = "r_tag")
	private String tag;
	/**
	 * 消息通知接收人id
	 */
	@TableField(value = "r_to")
	private String toUid;
	/**
	 * 消息通知接收人
	 */
	@TableField(exist = false)
	private String toUname;
	/**
	 * 消息通知标题（可能包含变量）
	 */
	@TableField(value = "r_title")
	private String title;
	/**
	 * 消息通知内容（可能包含变量）
	 */
	@TableField(value = "r_content")
	private String content;
	/**
	 * 消息通知模板id（系统内信息模板、微信订阅消息等模板id）
	 */
	@TableField(value = "r_tid")
	private String tid;
	/**
	 * 消息通知关联业务id
	 */
	@TableField(value = "t_status")
	private String bid;
	/**
	 * 通知信息关联数据载体,JOSN格式的数据
	 */
	@TableField(value = "r_payload")
	private String payload;
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	@TableField(value = "r_status")
	private String status;
	/**
	 * 消息通知创建时间
	 */
	@TableField(value = "create_time")
	private String time24;
	/**
	 * 消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）
	 */
	@TableField(value = "t_status")
	private String route;
	/**
	 * 模糊搜索关键字
	 */
	@TableField(exist = false)
	private String keywords;

}
