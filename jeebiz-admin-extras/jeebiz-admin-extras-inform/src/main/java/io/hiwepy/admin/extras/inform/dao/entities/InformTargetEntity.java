/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.dao.entities;

import io.hiwepy.admin.extras.inform.emums.InformToType;
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
@TableName("sys_inform_targets")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InformTargetEntity extends PaginationEntity<InformTargetEntity> {

	/**
	 * 消息通知对象主键id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 消息通知事件id
	 */
	@TableField(value="from_id")
	private String eventId;
	/**
	 * 消息通知事件通知对象类型：（ORG:组织机构、ROLE:角色、POST：岗位、USER：人员）
	 */
	@TableField(value="to_type")
	private InformToType toType;
	/**
	 * 消息通知接收对象id
	 */
	@TableField(value = "to_id")
	private String targetId;

}
