/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.dao.entities;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Alias(value = "DeviceBindModel")
@TableName(value = "device_users", keepGlobalPrefix = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DeviceBindModel extends Model<DeviceBindModel> implements Cloneable {

    /**
	 * 主键，自增
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 应用ID
	 */
	@TableField(value = "u_app_id")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@TableField(value = "u_app_channel")
	private String appChannel;
	/**
	 *应用版本号
	 */
	@TableField(value = "u_app_version")
	private String appVersion;
	/**
	 *设备记录ID
	 */
	@TableField(value = "device_id")
    private Long deviceId;
	/**
	 * 是否删除 0未删除 1已删除
	 */
	@TableField(value = "is_delete")
	private Integer isDelete;
	/**
	 * 创建人id
	 */
	@TableField(value = "creator")
	private Long creator;
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 更新人id
	 */
	@TableField(value = "modifyer")
	private Long modifyer;
	/**
	 * 更新时间
	 */
	@TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
	private Date modifyTime;
    
}