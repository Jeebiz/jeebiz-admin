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

@ApiModel(value = "InformSendBO", description = "发起推送信息BO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class InformSendBO {
	
	/**
	 * 推送调试（务必保证当前对象不能直接作为Controller参数对象，以免发生生产事故）
	 */
	@ApiModelProperty(name = "debug", required = true, value = "推送调试")
	private boolean debug;
	/**
	 * 应用ID
	 */
	@ApiModelProperty(name = "appId", required = true, value = "应用ID")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@ApiModelProperty(name = "appChannel", required = true, value = "应用渠道编码")
	private String appChannel;
	/**
	 * 应用版本号
	 */
	@ApiModelProperty(name = "appVer", required = true, value = "应用版本号")
	private String appVer;
	/**
	 * 请求来源IP地址
	 */
	@ApiModelProperty(name = "ipAddress", required = true, value = "请求来IP地址")
	private String ipAddress;
	/**
	 * 唤起推送的用户uid
	 */
	@ApiModelProperty(name = "userId", required = true, value = "唤起推送的用户uid")
	private String userId;
	/**
	 * 推送类型（1：正常推送，2：失败重试）
	 */
	@ApiModelProperty(name = "sendType", value = "推送类型（1：正常推送，2：失败重试）")
	private Integer sendType;
	/**
	 * 推送渠道
	 */
	@ApiModelProperty(name = "channel", required = true, value = "推送渠道")
	private InformSendChannel channel;
	/**
	 * 流水编号
	 */
	@ApiModelProperty(name = "flowNo", value = "流水编号")
	private String flowNo;
	
}
