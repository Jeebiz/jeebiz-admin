/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.dao.entities;

import java.util.List;

import io.hiwepy.admin.extras.inform.emums.InformFromType;
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
@TableName("sys_inform_records")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InformRecordEntity extends PaginationEntity<InformRecordEntity> {

	/**
	 * 消息通知记录id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 消息通知记录来源类型：（EVENT:消息通知事件、SEND:用户主动发送）
	 */
	@TableField(value = "from_type")
	private InformFromType fromType;
	/**
	 * 消息通知发送人id/事件Id
	 */
	@TableField(value = "from_id")
	private String fromId;
	/**
	 * 消息通知发送人
	 */
	@TableField(exist = false)
	private String fromUname;
	/**
	 * 消息通知发送通道
	 */
	@TableField(value = "channel")
	private InformSendChannel channel;
	/**
	 * 消息通知标题
	 */
	@TableField(value = "title")
	private String title;
	/**
	 * 消息通知内容
	 */
	@TableField(value = "content")
	private String content;
	/**
	 * 消息通知对应平台内的模板id
	 */
	@TableField(value = "template_id")
	private String templateId;
	/**
	 * 消息通知变量载体,JOSN格式的数据
	 */
	@TableField(value = "payload")
	private String payload;
	/**
	 * 消息通知接收人id
	 */
	@TableField(value = "receiver_id")
	private String receiverId;
	/**
	 * 消息通知接收人
	 */
	@TableField(exist = false)
	private String receiverUname;
	/**
	 * 消息通知处理流水编号
	 */
	@TableField(value = "flow_no")
	private String flowNo;
	/**
	 * 消息通知关联业务id
	 */
	@TableField(value = "biz_id")
	private String bizId;
	/**
	 * 消息通知阅读状态：（0:未阅读、1:已阅读）
	 */
	@TableField(value = "status")
	private Integer status;
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
	 * 消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）
	 */
	@TableField(value = "route")
	private String route;
	/**
	 * 消息通知创建时间
	 */
	@TableField(value = "create_time")
	private String time24;
	/**
	 * 模糊搜索关键字
	 */
	@TableField(exist = false)
	private String keywords;

}
