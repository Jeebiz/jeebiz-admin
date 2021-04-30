/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.dao.entities;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.InformTarget;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@SuppressWarnings("serial")
@Alias(value = "InformTemplateModel")
@Getter
@Setter
@ToString
public class InformTemplateModel extends PaginationModel<InformTemplateModel> {

	/**
	 * 消息通知模板id
	 */
	private String id;
	/**
	 * 消息通知模板创建人id
	 */
	private String uid;
	/**
	 * 消息通知模板创建人
	 */
	private String uname;
	/**
	 * 消息通知模板面向对象
	 */
	private InformTarget target;
	/**
	 * 消息通知模板的发送提供者
	 */
	private InformProvider provider;
	/**
	 * 消息通知模板标题（可能包含变量）
	 */
	private String title;
	/**
	 * 消息通知模板内容（可能包含变量）
	 */
	private String content;
	/**
	 * 消息通知模板对应第三方平台内的模板id
	 */
	private String tid;
	/**
	 * 消息通知模板变量载体,JOSN格式的数据
	 */
	private String payload;
	/**
	 * 消息通知模板状态：（0:停用、1:启用）
	 */
	private String status;
	/**
	 * 消息通知模板创建时间
	 */
	private String time24;
	/**
	 * 消息通知模板已发消息总数
	 */
	private Integer total;
	/**
	 * 消息通知模板已发消息未读总数
	 */
	private Integer unread;
	/**
	 * 模糊搜索关键字
	 */
	private String keywords;
	
}
