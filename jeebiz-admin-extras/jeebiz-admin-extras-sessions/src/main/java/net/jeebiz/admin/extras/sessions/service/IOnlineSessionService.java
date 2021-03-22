/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.service;

import java.util.List;

import net.jeebiz.admin.extras.sessions.dao.entities.OnlineSessionModel;
import net.jeebiz.admin.extras.sessions.web.vo.OnlineSessionVo;
import net.jeebiz.boot.api.service.BaseService;

public interface IOnlineSessionService extends BaseService<OnlineSessionModel> {

	List<OnlineSessionVo> getActiveSessions();

	void offline(String sessionId);

	void online(OnlineSessionModel onlineSession);

	boolean forceLogout(String sessionId);
			 
}
