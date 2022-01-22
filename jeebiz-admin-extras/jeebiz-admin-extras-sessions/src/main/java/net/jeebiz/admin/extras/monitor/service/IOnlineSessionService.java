/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jeebiz.admin.extras.monitor.dao.entities.SessionEntity;
import net.jeebiz.admin.extras.monitor.web.dto.OnlineSessionDTO;
import net.jeebiz.admin.extras.monitor.web.param.SessionQueryParam;
import net.jeebiz.boot.api.service.IBaseService;

public interface IOnlineSessionService extends IBaseService<SessionEntity> {

	List<OnlineSessionDTO> getActiveSessions(SessionQueryParam queryParam, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request);
	

	OnlineSessionDTO getActiveSession(String sessionId, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request);

	boolean kickout(String sessionId, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request);
	
	int offline(String sessionId);

	int online(SessionEntity onlineSession);

			 
}
