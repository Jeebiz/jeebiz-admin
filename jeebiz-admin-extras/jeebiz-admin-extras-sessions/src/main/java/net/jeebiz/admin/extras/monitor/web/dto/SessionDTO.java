/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.monitor.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在线会话信息
 */
@ApiModel(value = "SessionDTO", description = "在线会话信息DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

	/** 会话id */
	@ApiModelProperty(value = "sessionId", dataType = "String", notes = "会话id")
	private String sessionId;
	/** 主机地址 */
	@ApiModelProperty(value = "host", dataType = "String", notes = "主机地址")
	private String host;
	/** 登录时间 */
	@ApiModelProperty(value = "startTimestamp", dataType = "String", notes = "登录时间")
	@JsonFormat(pattern = "")
	private Date startTimestamp;
	/** 最后访问时间 */
	@ApiModelProperty(value = "lastAccessTime", dataType = "String", notes = "最后访问时间")
	@JsonFormat(pattern = "")
	private Date lastAccessTime;
	/** 会话多久后过期（毫秒） */
	@ApiModelProperty(value = "timeout", dataType = "Long", notes = "会话多久后过期（毫秒）")
	private long timeout;
	/** 用户浏览器类型 */
	@ApiModelProperty(value = "userAgent", dataType = "String", notes = "用户浏览器类型")
	protected String userAgent;
	/** 用户登录时系统IP */
	@ApiModelProperty(value = "systemHost", dataType = "String", notes = "用户登录时系统IP")
	protected String systemHost;
	/** 在线状态 */
	@ApiModelProperty(value = "status", dataType = "Long", notes = "在线状态")
	protected String status;
	/** 已强制退出:1:是，0:否 */
	@ApiModelProperty(value = "forceLogout", dataType = "String", notes = "已强制退出:1:是，0:否", allowableValues = "1,0")
	private String forceLogout;

	/** 最后登录时间 */
	private Date lastLoginTimestamp;
	/** 最后退出时间 */
	private Date lastStopTimestamp;
	/** 登录次数 */
	private Integer loginCount = 0;
	/** 总的在线时长（秒为单位） */
	private Long totalOnlineTime = 0L;

}
