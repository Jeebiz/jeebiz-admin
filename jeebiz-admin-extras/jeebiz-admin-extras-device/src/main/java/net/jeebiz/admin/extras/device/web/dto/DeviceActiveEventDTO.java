/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DeviceActiveEventDTO", description = "设备激活操作DTO")
@Data
public class DeviceActiveEventDTO {
	
	/**
	 * 客户端设备唯一标识
	 */
	@ApiModelProperty(name = "deviceIMEI", required = true, dataType = "String", value = "客户端设备唯一标识")
	private String deviceIMEI;
	/**
	 * 客户端设备型号
	 */
	@ApiModelProperty(name = "deviceModel", required = true, dataType = "String", value = "客户端设备型号")
	private String deviceModel;

	@ApiModelProperty(name = "appId", required = true, dataType = "String", value = "应用id")
	private String appId;

	@ApiModelProperty(name = "appChannel", required = true, dataType = "String", value = "应用渠道编码")
	private String appChannel;

	@ApiModelProperty(name = "appVersion", required = true, dataType = "String", value = "应用版本号")
	private String appVersion;




}
