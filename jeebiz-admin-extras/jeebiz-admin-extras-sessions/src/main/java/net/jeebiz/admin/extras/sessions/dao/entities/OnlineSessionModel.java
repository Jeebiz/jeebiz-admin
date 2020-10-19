/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.dao.entities;

import java.text.SimpleDateFormat;

import org.apache.ibatis.type.Alias;
import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession.OnlineStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.jeebiz.boot.api.dao.entities.PaginationModel;

@Alias("OnlineSessionModel")
@SuppressWarnings("serial")
@Getter
@Setter
@ToString
public class OnlineSessionModel extends PaginationModel<OnlineSessionModel> {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	/**
	 * 回话记录ID
	 */
	private String id;
	/** 当前登录的用户Id */
    private String userid;
    /** 当前登录的用户名称 */
    private String username;
	/** 用户会话ID编号 */
	private String sessionId;
	/** 用户主机地址 */
	private String host;
	/** 用户登录时间 */
	private String startTimestamp;
	/** 最后访问时间 */
	private String lastAccessTime;
	/** 会话多久后过期（毫秒） */
	private long timeout;
	/** 用户浏览器类型 */
	private String userAgent;
	/** 用户登录时系统IP */
	private String systemHost;
	/** 在线状态 */
	private String status;
	/** 已强制退出:1:是，0:否 */
	private String forceLogout;
	/** 备份的当前用户会话 */
    private OnlineSession session;
    
	public OnlineSessionModel() {
		super();
	}

	public OnlineSessionModel(String sessionId, String host, String startTimestamp, String lastAccessTime,
			long timeout) {
		this.sessionId = sessionId;
		this.host = host;
		this.startTimestamp = startTimestamp;
		this.lastAccessTime = lastAccessTime;
		this.timeout = timeout;
	}

	public OnlineSessionModel(Session session) {
		this.sessionId = String.valueOf(session.getId());
		this.host = session.getHost();
		this.startTimestamp = dateFormat.format(session.getStartTimestamp());
		this.lastAccessTime = dateFormat.format(session.getLastAccessTime());
		this.timeout = session.getTimeout();
		if(session instanceof SimpleOnlineSession) {
			SimpleOnlineSession onlineSession = (SimpleOnlineSession) session;
			this.status = onlineSession.getStatus().getInfo();
		}
		if(Boolean.TRUE.equals(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY))) {
			this.status =  OnlineStatus.FORCE_LOGOUT.getInfo();
		}
	}

}
