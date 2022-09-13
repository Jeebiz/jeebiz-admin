/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.TeamMapper;
import io.hiwepy.admin.authz.org.dao.entities.TeamEntity;
import io.hiwepy.admin.authz.org.service.ITeamService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class TeamServiceImpl extends BaseServiceImpl<TeamMapper, TeamEntity> implements ITeamService{

	@Override
	public int getTeamCountByName(String name, String deptId, String teamId) {
		return getBaseMapper().getTeamCountByName(name, deptId, teamId);
	}

	@Override
	public int getStaffCount(String id) {
		return getBaseMapper().getStaffCount(id);
	}
	
}
