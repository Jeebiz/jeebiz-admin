/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.service;

import java.util.List;

import net.jeebiz.admin.extras.sessions.dao.entities.SessionEntity;
import net.jeebiz.admin.extras.sessions.web.dto.OnlineSessionDTO;
import net.jeebiz.boot.api.service.IBaseService;

public interface IOnlineSessionService extends IBaseService<SessionEntity> {

	List<OnlineSessionDTO> getActiveSessions();

	int offline(String sessionId);

	int online(SessionEntity onlineSession);

	boolean kickout(String sessionId);
			 
}
