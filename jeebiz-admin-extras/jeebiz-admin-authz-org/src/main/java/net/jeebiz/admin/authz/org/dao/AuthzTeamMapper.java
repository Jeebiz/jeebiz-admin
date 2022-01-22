package net.jeebiz.admin.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.org.dao.entities.AuthzTeamModel;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface AuthzTeamMapper extends BaseMapper<AuthzTeamModel> {

	/**
	 * 根据名称获取记录数
	 * @return
	 */
	public int getTeamCountByName(@Param("name") String name, @Param("deptId") String deptId, @Param("teamId") String teamId);
	
	/**
	 * 获取小组下成员梳理
	 * @param id
	 * @return
	 */
	public int getStaffCount(@Param("id") String id);
	
}
