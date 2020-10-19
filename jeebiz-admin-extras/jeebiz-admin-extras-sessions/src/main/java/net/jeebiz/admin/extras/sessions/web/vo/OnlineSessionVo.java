/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 在线会话信息
 */
@ApiModel(value = "OnlineSessionVo", description = "在线会话信息Vo")
@Getter
@Setter
@ToString
public class OnlineSessionVo {

	/** 会话ID */
	@ApiModelProperty(value = "sessionId", dataType = "String", notes = "会话ID")
	private String sessionId;
	/** 主机地址 */
	@ApiModelProperty(value = "host", dataType = "String", notes = "主机地址")
	private String host;
	/** 登录时间 */
	@ApiModelProperty(value = "startTimestamp", dataType = "String", notes = "登录时间")
	private String startTimestamp;
	/** 最后访问时间 */
	@ApiModelProperty(value = "lastAccessTime", dataType = "String", notes = "最后访问时间")
	private String lastAccessTime;
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

	public OnlineSessionVo() {
		super();
	}

	public OnlineSessionVo(String sessionId, String host, String startTimestamp, String lastAccessTime,
			long timeout) {
		this.sessionId = sessionId;
		this.host = host;
		this.startTimestamp = startTimestamp;
		this.lastAccessTime = lastAccessTime;
		this.timeout = timeout;
	}

}
