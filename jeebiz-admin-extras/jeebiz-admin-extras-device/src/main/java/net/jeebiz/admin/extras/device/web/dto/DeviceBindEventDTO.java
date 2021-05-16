/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "DeviceBindEventDTO", description = "设备绑定操作DTO")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceBindEventDTO {

	/**
	 * 设备关联的用户ID（用户信息表ID）
	 */
	@ApiModelProperty(name = "uid", dataType = "Long", value = "设备关联的用户ID（用户信息表ID）")
	private String uid;
	/**
	 * 应用ID
	 */
	@ApiModelProperty(name = "appId", required = true, dataType = "String", value = "应用ID")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@ApiModelProperty(name = "appChannel", required = true, dataType = "String", value = "应用渠道编码")
	private String appChannel;
	/**
	 * 应用版本号
	 */
	@ApiModelProperty(name = "appVer", required = true, dataType = "String", value = "应用版本号")
	private String appVer;
	/**
	 * IOS 6+的设备id
	 */
	@ApiModelProperty(name = "idfa", dataType = "String", value = "IOS 6+的设备id")
	private String idfa;
	/**
	 * IOS设备的识别码
	 */
	@ApiModelProperty(name = "openudid", dataType = "String", value = "IOS设备的识别码")
	private String openudid;
	/**
	 * 安卓id原值
	 */
	@ApiModelProperty(name = "androidid", dataType = "String", value = "安卓id原值")
	private String androidid;
	/**
	 * Android Q及更高版本的设备号，32位
	 */
	@ApiModelProperty(name = "oaid", dataType = "String", value = "Android Q及更高版本的设备号，32位")
	private String oaid;
	/**
	 * 安卓的设备 ID
	 */
	@ApiModelProperty(name = "imei", dataType = "String", value = "安卓的设备 ID")
	private String imei;
	/**
	 * 客户端设备型号
	 */
	@ApiModelProperty(name = "model", required = true, dataType = "String", value = "客户端设备型号")
	private String model;
	/**
	 * 移动设备mac地址
	 */
	@ApiModelProperty(name = "mac", dataType = "String", value = "移动设备mac地址")
	public String mac;
	/**
	 * 用户代理(User Agent)，一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
	 */
	@ApiModelProperty(name = "ua", dataType = "String", value = "用户代理(User Agent)")
	public String ua;
	/**
	 * 媒体投放系统获取的用户终端的公共IP地址
	 */
	@ApiModelProperty(name = "ip", dataType = "String", value = "媒体投放系统获取的用户终端的公共IP地址")
	public String ip;

}
