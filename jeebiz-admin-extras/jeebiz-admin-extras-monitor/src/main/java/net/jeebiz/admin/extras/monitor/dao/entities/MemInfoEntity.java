/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.dao.entities;

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
@TableName(value = "usage_mem", keepGlobalPrefix = true)
@Data
@EqualsAndHashCode(callSuper=false)
public class MemInfoEntity extends Model<MemInfoEntity> {

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
	 * 内存总量
	 */
	@TableField(value = "mem_total")
	private double total;

	/**
	 * 已用内存
	 */
	@TableField(value = "mem_used")
	private double used;

	/**
	 * 剩余内存
	 */
	@TableField(value = "mem_free")
	private double free;

	/**
	 * 内存使用率
	 */
	@TableField(value = "mem_usage")
	private double usage;
	
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	
}
