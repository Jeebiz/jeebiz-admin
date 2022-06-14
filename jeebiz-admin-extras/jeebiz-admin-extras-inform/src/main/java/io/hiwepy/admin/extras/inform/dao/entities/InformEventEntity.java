/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias(value = "InformEventEntity")
@TableName("sys_inform_event")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InformEventEntity extends PaginationEntity<InformEventEntity> {

	/**
	 * 消息通知事件id
	 */
	@TableId(value="id",type= IdType.ASSIGN_ID)
	private String id;
	/**
	 * 消息通知事件类型
	 */
	@TableField(value = "type")
	private String type;
	/**
	 * 消息通知事件行为
	 */
	@TableField(value = "channel")
	private InformSendChannel channel;
	/**
	 * 消息通知事件关联模板id
	 */
	@TableField(value = "template_id")
	private String templateId;
	/**
	 * 消息通知事件说明
	 */
	@TableField(value = "intro")
	private String intro;
	/**
	 * 消息通知事件状态：（0:停用、1:启用）
	 */
	@TableField(value = "status")
	private String status;
	/**
	 * 消息通知事件已发消息总数
	 */
	@TableField(exist = false)
	private Integer total;
	/**
	 * 消息通知事件已发消息未读总数
	 */
	@TableField(exist = false)
	private Integer unread;

}
