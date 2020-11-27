/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.InformTarget;

@ApiModel(value = "InformTemplateDTO", description = "消息通知模板对象DTO")
@Data
public class InformTemplateDTO {

	/**
	 * 消息通知模板ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "消息通知模板ID")
	private String id;
	/**
	 * 消息通知模板创建人ID
	 */
	@ApiModelProperty(name = "uid", dataType = "String", value = "消息通知模板创建人ID")
	private String uid;
	/**
	 * 消息通知模板创建人
	 */
	@ApiModelProperty(name = "uname", dataType = "String", value = "消息通知模板创建人")
	private String uname;	
	/**
	 * 消息通知推送对象
	 */
	@ApiModelProperty(name = "target", dataType = "String", value = "消息通知推送对象：（all：全部用户、users：指定用户）")
	private InformTarget target;
	/**
	 * 发送该模板消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", dataType = "InformType", value = "消息通知类型")
	private InformProvider provider;
	
	/**
	 * 消息通知接收人ID集合
	 */
	@ApiModelProperty(name = "toList", dataType = "java.util.List<String>", value = "消息通知接收人ID集合")
	private List<String> toList;
	
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
	 * 模板消息通知对应第三方平台内的模板ID
	 */
	@ApiModelProperty(name = "tid", dataType = "String", value = "模板消息通知对应第三方平台内的模板ID")
	private String tid;
	/**
	 * 模板消息通知变量载体,JOSN格式的数据
	 */
	@ApiModelProperty(name = "payload", dataType = "String", value = "模板消息通知变量载体,JOSN格式的数据")
	private String payload;
	/**
	 * 消息通知模板已发消息总数
	 */
	@ApiModelProperty(name = "total", dataType = "Integer", value = "消息通知模板已发消息总数")
	private Integer total;
	/**
	 * 消息通知模板已发消息未读总数
	 */
	@ApiModelProperty(name = "unread", dataType = "Integer", value = "消息通知模板已发消息未读总数")
	private Integer unread;
	/**
	 * 消息通知模板状态：（0:停用、1:启用）
	 */
	@ApiModelProperty(name = "status", dataType = "String", value = "消息通知模板状态：（0:停用、1:启用）")
	private String status;
	/**
	 * 消息通知模板创建时间
	 */
	@ApiModelProperty(name = "time24", dataType = "String", value = "消息通知创建时间")
	private String time24;
	
}
