/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.dao.entities;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@TableName(value = "sys_usage_cpu")
@Data
@EqualsAndHashCode(callSuper=false)
public class CpuInfoEntity extends Model<CpuInfoEntity> {

	/**
	 * 主键ID
	 */
	@TableId(value="id",type= IdType.AUTO)
	private String id;
	
	/** 
	 * 服务器实例ID
	*/
	@TableField(value = "instance_id")
    private String instanceId;

	/**
	 * CPU核心数
	 */
	@TableField(value = "cpu_num")
	private int cpuNum;

	/**
	 * CPU总的使用率
	 */
	@TableField(value = "cpu_total")
	private double total;

	/**
	 * CPU系统使用率
	 */
	@TableField(value = "cpu_sys")
	private double sys;

	/**
	 * CPU用户使用率
	 */
	@TableField(value = "cpu_user")
	private double user;

	/**
	 * CPU当前等待率
	 */
	@TableField(value = "cpu_wait")
	private double wait;

	/**
	 * CPU当前空闲率
	 */
	@TableField(value = "cpu_free")
	private double free;
	
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

}
