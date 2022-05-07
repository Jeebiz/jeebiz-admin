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
import io.hiwepy.admin.extras.inform.emums.InformTarget;
import io.hiwepy.boot.api.dao.entities.PaginationEntity;

@SuppressWarnings("serial")
@Alias(value = "InformTemplateEntity")
@TableName(value = "inform_templates", keepGlobalPrefix = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class InformTemplateEntity extends PaginationEntity<InformTemplateEntity> {

	/**
	 * 消息通知模板id
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	/**
	 * 消息通知模板面向对象
	 */
	@TableField(value = "t_target")
	private InformTarget target;
	/**
	 * 消息通知模板标题（可能包含变量）
	 */
	@TableField(value = "t_title")
	private String title;
	/**
	 * 消息通知模板内容（可能包含变量）
	 */
	@TableField(value = "t_content")
	private String content;
	/**
	 * 消息通知模板对应第三方平台内的模板id
	 */
	@TableField(value = "t_tid")
	private String tid;
	/**
	 * 消息通知模板变量载体,JOSN格式的数据
	 */
	@TableField(value = "t_payload")
	private String payload;
	/**
	 * 消息通知模板状态：（0:停用、1:启用）
	 */
	@TableField(value = "t_status")
	private String status;
	/**
	 * 消息通知模板创建人id
	 */
	@TableField(exist = false)
	private String uid;
	/**
	 * 消息通知模板创建人
	 */
	@TableField(exist = false)
	private String uname;
	/**
	 * 消息通知模板已发消息总数
	 */
	@TableField(exist = false)
	private Integer total;
	/**
	 * 消息通知模板已发消息未读总数
	 */
	@TableField(exist = false)
	private Integer unread;
	/**
	 * 模糊搜索关键字
	 */
	@TableField(exist = false)
	private String keywords;

}
