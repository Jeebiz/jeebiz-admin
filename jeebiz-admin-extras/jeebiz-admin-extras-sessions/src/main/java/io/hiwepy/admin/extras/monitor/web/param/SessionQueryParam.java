/**
 * Copyright (C) 2020 杭州快定网络股份有限公司 (http://kding.com).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.monitor.web.param;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ApiModel(value = "SessionQueryParam", description = "在线会话查询参数")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SessionQueryParam {

	/** 开始时间 */
	@ApiModelProperty(name = "beginTime", value = "开始时间")
	private String beginTime;

	/** 结束时间 */
	@ApiModelProperty(name = "endTime", value = "结束时间")
	private String endTime;
	/**
	 *  上一次拉取排行榜时后台返回给客户端的 Seq，初次拉取时为0
	 */
	@ApiModelProperty(name = "lastSequence", value = "上一次拉取排行榜时后台返回给客户端的 Seq，初次拉取时为0")
	@Min(value = 0,message = "最小值为0")
	private Long lastSequence;

}
