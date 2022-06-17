/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import io.hiwepy.admin.extras.inform.emums.InformSendChannel;

import java.util.Map;

@ApiModel(value = "InformSendBO", description = "发起推送信息BO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InformSendBO {

	/**
	 * 推送调试（务必保证当前对象不能直接作为Controller参数对象，以免发生生产事故）
	 */
	private boolean debug;
	/**
	 * 应用ID
	 */
	private String appId;
	/**
	 * 应用渠道编码
	 */
	private String appChannel;
	/**
	 * 应用版本号
	 */
	private String appVer;
	/**
	 * 请求来源IP地址
	 */
	private String ipAddress;
	/**
	 * 唤起推送的用户uid
	 */
	private Long userId;
	/**
	 * 推送类型（1：正常推送，2：失败重试）
	 */
	private Integer sendType;
	/**
	 * 业务ID
	 */
	private String bizId;
	/**
	 * 推送渠道
	 */
	private InformSendChannel channel;
	/**
	 * 消息通知标题（可能包含变量）
	 */
	private String title;
	/**
	 * 消息通知签名（短信消息模板需要使用）
	 */
	private String signature;
	/**
	 * 消息通知内容（可能包含变量）
	 */
	private String content;
	/**
	 * 模板消息通知对应第三方平台内的模板id
	 */
	private String templateId;
	/**
	 * 消息通知模板参数
	 */
	private Map<String, Object> templateParams;
	/**
	 * 消息通知变量载体,JOSN格式的数据
	 */
	private String payload;
	/**
	 * 流水编号
	 */
	private String flowNo;
	
}
