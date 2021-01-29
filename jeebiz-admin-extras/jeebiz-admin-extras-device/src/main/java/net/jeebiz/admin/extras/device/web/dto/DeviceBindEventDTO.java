/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "DeviceBindEventDTO", description = "设备绑定操作DTO")
@Data
public class DeviceBindEventDTO {
	
	/**
	 * 客户端设备唯一标识
	 */
	@ApiModelProperty(name = "deviceIMEI", required = true, dataType = "String", value = "客户端设备唯一标识")
	private String deviceIMEI;
	/**
	 * 设备关联的用户ID（用户信息表ID）
	 */
	@ApiModelProperty(name = "uid", dataType = "Long", value = "设备关联的用户ID（用户信息表ID）")
	private Long uid;

	@ApiModelProperty(name = "appId", required = true, dataType = "String", value = "应用ID")
	private String appId;

	@ApiModelProperty(name = "appChannel", required = true, dataType = "String", value = "应用渠道编码")
	private String appChannel;

	@ApiModelProperty(name = "appVersion", required = true, dataType = "String", value = "应用版本号")
	private String appVersion;

}
