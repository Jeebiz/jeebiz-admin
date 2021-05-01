/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.logbiz.dao.entities;

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
import net.jeebiz.boot.api.dao.entities.PaginationEntity;

/**
 * 功能操作日志信息表Model
 */
@SuppressWarnings("serial")
@Alias("BizLogModel")
@TableName(value = "log_biz", keepGlobalPrefix = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BizLogModel extends PaginationEntity<BizLogModel> {

	/**
	 * 日志id
	 */
	@TableId(value="log_id",type= IdType.AUTO)
	private String id;
	/**
	 * 功能模块
	 */
	@TableField(value = "log_module")
	private String module;
	/**
	 * 业务名称
	 */
	@TableField(value = "log_biz")
	private String business;
	/**
	 * 操作类型
	 */
	@TableField(value = "log_opt")
	private String opt;
	/**
	 * 功能操作请求来源IP地址
	 */
	@TableField(value = "log_addr")
	private String addr;
	/**
	 * 功能操作请求来源IP所在地点
	 */
	@TableField(value = "log_location")
	private String location;
	/**
	 * 功能操作描述
	 */
	@TableField(value = "log_msg")
	private String msg;
	/**
	 * 功能操作异常信息
	 */
	@TableField(value = "log_excp")
	private String exception;
	/**
	 *设备记录ID
	 */
	@TableField(value = "device_id")
    private String deviceId;
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
	 * 认证发生时间
	 */
	@TableField(exist = false)
	private String timestamp;
	/**
	 * 认证授权对象id
	 */
	@TableField(exist = false)
	private String userId;
	/**
	 * 认证授权人
	 */
	@TableField(exist = false)
	private String nickname;
	/**
	 * 认证授权起始时间
	 */
	@TableField(exist = false)
	private String begintime;
	/**
	 * 认证授权结束时间
	 */
	@TableField(exist = false)
	private String endtime;
	/**
	 * 模糊搜索关键字
	 */
	@TableField(exist = false)
	private String keywords;
}
