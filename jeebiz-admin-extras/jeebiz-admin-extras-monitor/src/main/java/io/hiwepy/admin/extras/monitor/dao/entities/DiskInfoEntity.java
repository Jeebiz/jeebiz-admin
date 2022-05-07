/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.monitor.dao.entities;

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
@TableName(value = "usage_disk", keepGlobalPrefix = true)
@Data
@EqualsAndHashCode(callSuper=false)
public class DiskInfoEntity extends Model<DiskInfoEntity> {

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
	 * 盘符路径
	 */
	@TableField(value = "disk_mount")
	private String mount;

	/**
	 * 盘符类型
	 */
	@TableField(value = "disk_name")
	private String name;
	
	/**
	 * 文件类型
	 */
	@TableField(value = "disk_type")
	private String type;

	/**
	 * 磁盘总大小
	 */
	@TableField(value = "disk_total")
	private String total;

	/**
	 * 已用磁盘大小
	 */
	@TableField(value = "disk_used")
	private String used;

	/**
	 * 剩余磁盘大小
	 */
	@TableField(value = "disk_free")
	private String free;

	/**
	 * 磁盘使用率
	 */
	@TableField(value = "disk_usage")
	private double usage;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

}
