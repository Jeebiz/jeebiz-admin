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
@Alias(value = "DeviceActiveModel")
@TableName(value = "device_activate", keepGlobalPrefix = true)
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
	 * 应用ID
	 */
	@TableField(value = "app_id")
	private String appId;
	/**
	 * 应用渠道编码
	 */
	@TableField(value = "app_channel")
	private String appChannel;
	/**
	 *应用版本号
	 */
	@TableField(value = "app_version")
	private String appVersion;
	/**
	 * IOS 6+的设备id
	 */
	@TableField(value = "device_idfa")
	private String idfa;
	/**
	 * IOS 6+的设备id的md5摘要，32位
	 */
	@TableField(value = "device_idfa_md5")
	private String idfa_md5;
	/**
	 * IOS设备的识别码
	 */
	@TableField(value = "device_openudid")
	private String openudid;
	/**
	 * IOS设备的识别码的md5摘要，32位
	 */
	@TableField(value = "device_openudid_md5")
	private String openudid_md5;
	/**
	 * 安卓id原值
	 */
	@TableField(value = "device_androidid")
	private String androidid;
	/**
	 * 安卓id原值的md5摘要，32位
	 */
	@TableField(value = "device_androidid_md5")
	private String androidid_md5;
	/**
	 * Android Q及更高版本的设备号，32位
	 */
	@TableField(value = "device_oaid")
	private String oaid;
	/**
	 * Android Q及更高版本的设备号的md5摘要，32位
	 */
	@TableField(value = "device_oaid_md5")
	private String oaid_md5;
	/**
	 * 安卓的设备 ID
	 */
	@TableField(value = "device_imei")
	private String imei;
	/**
	 * 安卓的设备 ID的md5摘要，32位
	 */
	@TableField(value = "device_imei_md5")
	private String imei_md5;
	/**
	 * 客户端设备型号
	 */
	@TableField(value = "device_model")
	private String model;
	/**
	 * 移动设备mac地址
	 */
	@TableField(value = "device_mac")
	public String mac;
	/**
	 * 媒体投放系统获取的用户终端的公共IP地址
	 */
	@TableField(value = "device_ip")
	public String ip;
	/**
	 * 用户代理(User Agent)，一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
	 */
	@TableField(value = "device_ua")
	public String ua;
	/**
	 * 是否可用（ 0：不可用，1：可用）
	 */
	@TableField(value = "status")
	private int status;
    
}