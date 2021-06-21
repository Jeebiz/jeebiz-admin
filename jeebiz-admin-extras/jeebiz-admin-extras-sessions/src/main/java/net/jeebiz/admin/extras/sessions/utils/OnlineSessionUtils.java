/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.utils;

import java.text.SimpleDateFormat;

import org.apache.shiro.session.mgt.OnlineSession;

import net.jeebiz.admin.extras.sessions.dao.entities.SessionEntity;

public class OnlineSessionUtils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

	public static final SessionEntity toOnlineSession(OnlineSession session) {
		
		SessionEntity online = new SessionEntity(dateFormat, session);
		
        online.setId(String.valueOf(session.getId()));
        online.setUserid(session.getUserid());
        online.setUsername(session.getUsername());
        online.setUserAgent(session.getUserAgent());
        online.setAddress(session.getSystemHost());
        online.setSession(session);
        return online;
    }
	
}
