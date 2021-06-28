/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.dao.entities;

import java.text.SimpleDateFormat;

import org.apache.ibatis.type.Alias;
import org.apache.shiro.biz.web.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.OnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession;
import org.apache.shiro.session.mgt.SimpleOnlineSession.OnlineStatus;

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

@Alias("OnlineSessionModel")
@SuppressWarnings("serial")
@TableName(value = "sys_authz_user_sessions")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SessionEntity extends PaginationEntity<SessionEntity> {
	
	/**
	 * 回话记录id
	 */
	@TableId(value="s_id",type= IdType.AUTO)
	private String id;
	/** 当前登录的用户Id */
	@TableField(value = "s_uid")
    private String userid;
    /** 当前登录的用户名称 */
	@TableField(value = "s_uname")
    private String username;
	/** 用户会话id编号 */
	@TableField(value = "s_sessionId")
	private String sessionId;
	/** 用户主机地址 */
	@TableField(value = "s_host")
	private String host;
	/** 用户登录时间 */
	@TableField(value = "s_start_timestamp")
	private String startTimestamp;
	/** 最后访问时间 */
	@TableField(value = "s_last_access_time")
	private String lastAccessTime;
	/** 用户浏览器类型 */
	@TableField(value = "s_ua")
	private String userAgent;
	/** 用户登录时系统IP */
	@TableField(value = "s_address")
	private String address;
	/** 已强制退出:1:是，0:否 */
	@TableField(value = "s_force_logout")
	private String forceLogout;
	/** 备份的当前用户会话 */
	@TableField(value = "s_session")
    private OnlineSession session;
	/** 会话多久后过期（毫秒） */
	@TableField(value = "s_timeout")
	private long timeout;
	/** 在线状态（0：离线，1：在线） */
	@TableField(value = "s_status")
	private String status;

	public SessionEntity(String sessionId, String host, String startTimestamp, String lastAccessTime,
			long timeout) {
		this.sessionId = sessionId;
		this.host = host;
		this.startTimestamp = startTimestamp;
		this.lastAccessTime = lastAccessTime;
		this.timeout = timeout;
	}

	public SessionEntity(SimpleDateFormat dateFormat, Session session) {
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
