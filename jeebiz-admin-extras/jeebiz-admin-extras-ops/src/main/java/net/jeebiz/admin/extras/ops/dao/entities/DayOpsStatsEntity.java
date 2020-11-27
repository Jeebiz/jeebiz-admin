/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.ops.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.jeebiz.boot.api.dao.entities.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "KDING_OPS_STATS")
public class DayOpsStatsEntity extends BaseEntity<DayOpsStatsEntity> {

	/**
	 * 主键，自增
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private String id;
	/**
	 * 统计日期
	 */
	@TableField(value = "DAY")
	private String day;
	/**
	 * 新增注册用户数
	 */
	@TableField(value = "DR")
	private Long dr;
	/**
	 * 当日活跃用户数
	 */
	@TableField(value = "DAU")
	private Long dau;
	/**
	 * 当日购买会员用户数
	 */
	@TableField(value = "PAY_NUMBER")
	private Long number;
	/**
	 * 当日购买会员次数
	 */
	@TableField(value = "PAY_TOTAL")
	private Long total;
	/**
	 * 当日购买会员金额
	 */
	@TableField(value = "PAY_AMOUNT")
	private Double amount;
}
