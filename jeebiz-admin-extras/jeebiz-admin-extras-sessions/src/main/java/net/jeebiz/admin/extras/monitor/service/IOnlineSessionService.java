/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.service;

import java.util.List;

import net.jeebiz.admin.extras.monitor.dao.entities.SessionEntity;
import net.jeebiz.admin.extras.monitor.web.dto.OnlineSessionDTO;
import net.jeebiz.boot.api.service.IBaseService;

public interface IOnlineSessionService extends IBaseService<SessionEntity> {

	List<OnlineSessionDTO> getActiveSessions();

	int offline(String sessionId);

	int online(SessionEntity onlineSession);

	boolean kickout(String sessionId);
			 
}
