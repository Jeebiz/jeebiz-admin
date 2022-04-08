/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.monitor.web.dto;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleOnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession.OnlineStatus;

import hitool.core.lang3.time.DateFormats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.admin.extras.monitor.dao.entities.OnlineSessionEntity;

/**
 * 在线会话信息
 */
@ApiModel(value = "OnlineSessionDTO", description = "在线会话信息DTO")
@Getter
@Setter
@ToString
public class OnlineSessionDTO {

	/** 会话id */
	@ApiModelProperty(value = "sessionId", dataType = "String", notes = "会话id")
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

	public OnlineSessionDTO() {
		super();
	}

	public OnlineSessionDTO(String sessionId, String host, String startTimestamp, String lastAccessTime,
			long timeout) {
		this.sessionId = sessionId;
		this.host = host;
		this.startTimestamp = startTimestamp;
		this.lastAccessTime = lastAccessTime;
		this.timeout = timeout;
	}

	public static OnlineSessionDTO fromSession(Session session) {

		OnlineSessionDTO sessionDTO = new OnlineSessionDTO(String.valueOf(session.getId()), session.getHost(),
				DateFormatUtils.format(session.getStartTimestamp(), DateFormats.DATE_LONGFORMAT),
				DateFormatUtils.format(session.getLastAccessTime(), DateFormats.DATE_LONGFORMAT),
				session.getTimeout());

		if(session instanceof SimpleOnlineSession) {
			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
			sessionDTO.setStatus(onlineSession.getStatus().getInfo());
			sessionDTO.setUserAgent(onlineSession.getUserAgent());
			sessionDTO.setSystemHost(onlineSession.getSystemHost());
		}
		if(Boolean.TRUE.equals(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY))) {
			sessionDTO.setStatus(OnlineStatus.FORCE_LOGOUT.getInfo());
		}

		return sessionDTO;
	}

	public static OnlineSessionDTO fromSessionEntity(OnlineSessionEntity onlineSessionEntity) {

		OnlineSessionDTO sessionDTO = new OnlineSessionDTO(String.valueOf(onlineSessionEntity.getId()), onlineSessionEntity.getHost(),
				DateFormatUtils.format(onlineSessionEntity.getStartTimestamp(), DateFormats.DATE_LONGFORMAT),
				DateFormatUtils.format(onlineSessionEntity.getLastAccessTime(), DateFormats.DATE_LONGFORMAT),
				onlineSessionEntity.getTimeout());

		sessionDTO.setStatus(onlineSessionEntity.getStatus());
		sessionDTO.setUserAgent(onlineSessionEntity.getUserAgent());
		sessionDTO.setSystemHost(onlineSessionEntity.getHost());

		if(Boolean.TRUE.equals(Boolean.parseBoolean(onlineSessionEntity.getForceLogout()))) {
			sessionDTO.setStatus(OnlineStatus.FORCE_LOGOUT.getInfo());
		}

		return sessionDTO;
	}



}
