/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.ops.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "DayOpsStatsDTO", description = "每日运营数据统计DTO")
@Data
public class DayOpsStatsDTO {

	/**
	 * 日期
	 */
	private String date;
	/**
	 * 新增注册用户数
	 */
	private Long dayRegisterTotal;
	/**
	 * 当日活跃用户数（登录人数）
	 */
	private Long dayLoginTotal;
	/**
	 * 当日付费次数
	 */
	private Long dayPayNumber;
	/**
	 * 当日付费人数
	 */
	private Long dayPayTotal;
	/**
	 * 今日购买VIP付费金额（元）
	 */
	private Double dayPayAmount;
	
}