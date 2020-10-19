/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sessions.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.sessions.dao.ILastOnlineSessionDao;
import net.jeebiz.admin.extras.sessions.dao.entities.LastOnlineSessionModel;
import net.jeebiz.admin.extras.sessions.service.ILastOnlineSessionService;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class LastOnlineSessionServiceImpl extends BaseServiceImpl<LastOnlineSessionModel, ILastOnlineSessionDao>
		implements ILastOnlineSessionService {
	
}
