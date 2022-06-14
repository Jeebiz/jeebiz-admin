/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.dao.entities;

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
import io.hiwepy.boot.api.dao.entities.PaginationEntity;

/**
 * 认证授权日志信息表Model
 */
@SuppressWarnings("serial")
@Alias(value = "AuthzLogEntity")
@TableName(value = "sys_log_authz")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthzLogEntity extends PaginationEntity<AuthzLogEntity> {
	
	/**
	 * 日志id
	 */
	@TableId(value="log_id",type= IdType.AUTO)
	private String id;
	/**
	 * 认证授权类型（login:登录认证、logout:会话注销）
	 */
	@TableField(value = "log_opt")
	private String opt;
	/**
	 * 认证协议：CAS、HTTP、JWT、KISSO、LDAP、OAuth2、Openid、SMAL等
	 */
	@TableField(value = "log_protocol")
	private String protocol;
	/**
	 * 认证请求来源IP地址
	 */
	@TableField(value = "log_addr")
	private String addr;
	/**
	 * 认证请求来源IP所在地点
	 */
	@TableField(value = "log_location")
	private String location;
	/**
	 * 认证授权结果：（fail:失败、success:成功）
	 */
	@TableField(value = "log_status")
	private String status;
	/**
	 * 认证授权请求信息
	 */
	@TableField(value = "log_msg")
	private String msg;
	/**
	 * 认证授权异常信息
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
	 * 功能操作发生时间
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
	
}
