/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.monitor.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.extras.monitor.dao.LastOnlineSessionMapper;
import io.hiwepy.admin.extras.monitor.dao.entities.LastOnlineSessionModel;
import io.hiwepy.admin.extras.monitor.service.ILastOnlineSessionService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class LastOnlineSessionServiceImpl extends BaseServiceImpl<LastOnlineSessionMapper, LastOnlineSessionModel>
		implements ILastOnlineSessionService {
	
}
