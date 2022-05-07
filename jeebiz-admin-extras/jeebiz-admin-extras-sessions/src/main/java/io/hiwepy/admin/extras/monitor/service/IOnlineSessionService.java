/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.monitor.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import io.hiwepy.admin.extras.monitor.dao.entities.OnlineSessionEntity;
import io.hiwepy.admin.extras.monitor.web.dto.OnlineSessionDTO;
import io.hiwepy.admin.extras.monitor.web.param.SessionQueryParam;
import io.hiwepy.boot.api.service.IBaseService;

public interface IOnlineSessionService extends IBaseService<OnlineSessionEntity> {

	List<OnlineSessionDTO> getActiveSessions(SessionQueryParam queryParam, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request);


	OnlineSessionDTO getActiveSession(String sessionId, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request);

	boolean kickout(String sessionId, String appId, String appChannel, String appVersion,
			String languageCode, HttpServletRequest request);

	int offline(String sessionId);

	int online(OnlineSessionEntity onlineSession);


}
