/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.utils;

import org.apache.shiro.session.mgt.OnlineSession;

import net.jeebiz.admin.extras.sessions.dao.entities.LastOnlineSessionModel;
import net.jeebiz.admin.extras.sessions.dao.entities.OnlineSessionModel;

public class OnlineSessionUtils {

	public static final OnlineSessionModel toOnlineSession(OnlineSession session) {
		OnlineSessionModel online = new OnlineSessionModel(session);
        online.setId(String.valueOf(session.getId()));
        online.setUserid(session.getUserid());
        online.setUsername(session.getUsername());
        online.setUserAgent(session.getUserAgent());
        online.setSystemHost(session.getSystemHost());
        online.setSession(session);
        return online;
    }
	
	public static final LastOnlineSessionModel toLastOnlineSession(OnlineSession online) {
        LastOnlineSessionModel lastOnline = new LastOnlineSessionModel();
        lastOnline.setHost(online.getHost());
        lastOnline.setUserid(online.getUserid());
        lastOnline.setUsername(online.getUsername());
        lastOnline.setUserAgent(online.getUserAgent());
        lastOnline.setSystemHost(online.getSystemHost());
        lastOnline.setUid(String.valueOf(online.getId()));
        lastOnline.setLastLoginTimestamp(online.getStartTimestamp());
        lastOnline.setLastStopTimestamp(online.getLastAccessTime());
        return lastOnline;
    }

    public static final void merge(LastOnlineSessionModel from, LastOnlineSessionModel to) {
        to.setHost(from.getHost());
        to.setUserid(from.getUserid());
        to.setUsername(from.getUsername());
        to.setUserAgent(from.getUserAgent());
        to.setSystemHost(from.getSystemHost());
        to.setUid(String.valueOf(from.getUid()));
        to.setLastLoginTimestamp(from.getLastLoginTimestamp());
        to.setLastStopTimestamp(from.getLastStopTimestamp());
    }
	
}
