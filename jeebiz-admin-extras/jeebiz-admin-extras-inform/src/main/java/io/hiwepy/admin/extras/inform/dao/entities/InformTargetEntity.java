/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.dao.entities;

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
import io.hiwepy.boot.api.dao.entities.PaginationEntity;

@SuppressWarnings("serial")
@Alias(value = "InformTargetEntity")
@TableName(value = "inform_targets", keepGlobalPrefix = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InformTargetEntity extends PaginationEntity<InformTargetEntity> {

	/**
	 * 消息通知模板id
	 */
	@TableId(value="t_id",type= IdType.AUTO)
	private String id;
	/**
	 * 消息通知模板id
	 */
	@TableField(value="tmp_id")
	private String tid;
	/**
	 * 消息通知接收人id
	 */
	@TableField(value = "t_uid")
	private String uid;
	/**
	 * 消息通知发送状态：（0:待发送、1:已发送）
	 */
	@TableField(value = "t_status")
	private String status;
	/**
	 * 消息通知发送时间
	 */
	@TableField(value = "time24")
	private String time24;

}
