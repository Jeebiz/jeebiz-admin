/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.web.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.InformTarget;

@ApiModel(value = "InformTemplateRenewVo", description = "更新消息通知模板Vo")
@Getter
@Setter
@ToString
public class InformTemplateRenewVo {
	
	/**
	 * 消息通知模板ID
	 */
	@ApiModelProperty(name = "id", dataType = "String", value = "消息通知模板ID")
	@NotBlank(message = "消息通知模板ID不能为空")
	private String id;
	/**
	 * 消息通知推送对象
	 */
	@ApiModelProperty(name = "target", required = true, dataType = "InformTarget", value = "消息通知推送对象")
	@NotEmpty(message = "消息通知推送对象不能为空")
	private InformTarget target;
	/**
	 * 发送该模板消息通知的提供者
	 */
	@ApiModelProperty(name = "provider", required = true, dataType = "InformProvider", value = "消息通知类型")
	@NotEmpty(message = "消息通知的提供者不能为空")
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
	
}
