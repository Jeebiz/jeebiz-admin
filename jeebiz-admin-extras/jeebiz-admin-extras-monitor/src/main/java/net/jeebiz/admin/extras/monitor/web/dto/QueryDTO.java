/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.web.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据筛选条件
 */
@ApiModel(value = "QueryDTO", description = "数据筛选条件DTO")
@Data
public class QueryDTO {

	/** 数据筛选开始时间 */
	@ApiModelProperty(value = "beginTime", notes = "在线状态")
	private Date beginTime;

	/** 数据筛选结束时间 */
	@ApiModelProperty(value = "endTime", notes = "在线状态")
	private Date endTime;

}
