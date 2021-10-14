/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会话强制离线参数
 */
@ApiModel(value = "SessionKickoutParam", description = "会话强制离线参数")
@Data
public class SessionKickoutParam {

	/** 会话id */
	@ApiModelProperty(value = "sessionId", dataType = "String", notes = "会话id")
	private String sessionId;

}
