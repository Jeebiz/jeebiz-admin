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
@TableName(value = "usage_jvm", keepGlobalPrefix = true)
@Data
@EqualsAndHashCode(callSuper=false)
public class JvmInfoEntity extends Model<JvmInfoEntity> {

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
	 * JDK名称
	 */
	@TableField(value = "jdk_name")
	private String name;

	/**
	 * JDK版本
	 */
	@TableField(value = "jdk_version")
	private String version;

	/**
	 * JDK路径
	 */
	@TableField(value = "jdk_home")
	private String home;
	
	/**
	 * 当前JVM占用的内存总数(M)
	 */
	@TableField(value = "jvm_total")
	private double total;

	/**
	 * JVM最大可用内存总数(M)
	 */
	@TableField(value = "jvm_max")
	private double max;

	/**
	 * JVM空闲内存(M)
	 */
	@TableField(value = "jvm_free")
	private double free;

	/**
	 * JVM已用内存(M)
	 */
	@TableField(value = "jvm_used")
	private double used;

	/**
	 * JVM使用率
	 */
	@TableField(value = "jvm_usage")
	private double usage;

	/**
	 * JDK启动时间
	 */
	@TableField(value = "jvm_start_time")
	private String startTime;

	/**
	 * JDK运行时间
	 */
	@TableField(value = "jvm_run_time")
	private String runTime;
	
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

}
