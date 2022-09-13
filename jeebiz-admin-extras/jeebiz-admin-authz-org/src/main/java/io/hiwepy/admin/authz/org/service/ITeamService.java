/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service;

import io.hiwepy.admin.authz.org.dao.entities.TeamEntity;
import io.hiwepy.boot.api.service.IBaseService;

public interface ITeamService extends IBaseService<TeamEntity> {
	
	/**
	 * 根据名称获取记录数
	 * @return
	 */
	public int getTeamCountByName(String name, String deptId, String teamId);
	
	/**
	 * 获取小组下成员梳理
	 * @param id
	 * @return
	 */
	public int getStaffCount(String id);
	
}
