/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.dao.entities;

import org.apache.ibatis.type.Alias;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.jeebiz.boot.api.dao.entities.BaseEntity;


@SuppressWarnings("serial")
@Alias(value = "DeviceActivateModel")
@TableName(value = "KDING_DEVICE_ACTIVATE")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DeviceActivateModel extends BaseEntity<DeviceActivateModel> {

	/**
	 * 主键，自增
	 */
	@TableId(value="id",type= IdType.AUTO)
	private Long id;
	/**
	 * 应用id
	 */
	@TableField(value = "APP_id")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@TableField(value = "APP_CHANNEL")
	private String appChannel;
	/**
	 *应用版本号
	 */
	@TableField(value = "APP_VERSION")
	private String appVersion;
	/**
	 *设备唯一标识IMEI
	 */
	@TableField(value = "DEVICE_IMEI")
    private String deviceImei;
	/**
	 *设备型号（手机型号）
	 */
	@TableField(value = "DEVICE_MODEL")
    private String deviceModel;
    
}