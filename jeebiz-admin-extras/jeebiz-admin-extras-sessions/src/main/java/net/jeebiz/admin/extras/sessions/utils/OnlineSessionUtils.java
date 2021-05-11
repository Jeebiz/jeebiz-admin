/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.utils;

import java.text.SimpleDateFormat;

import org.apache.shiro.session.mgt.OnlineSession;

import net.jeebiz.admin.extras.sessions.dao.entities.OnlineSessionModel;

public class OnlineSessionUtils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

	public static final OnlineSessionModel toOnlineSession(OnlineSession session) {
		
		OnlineSessionModel online = new OnlineSessionModel(dateFormat, session);
		
        online.setId(String.valueOf(session.getId()));
        online.setUserid(session.getUserid());
        online.setUsername(session.getUsername());
        online.setUserAgent(session.getUserAgent());
        online.setAddress(session.getSystemHost());
        online.setSession(session);
        return online;
    }
	
}
