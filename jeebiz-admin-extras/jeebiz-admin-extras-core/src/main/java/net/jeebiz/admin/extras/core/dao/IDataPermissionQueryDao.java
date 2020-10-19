/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionColumn;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;

import net.jeebiz.boot.api.dao.BaseDao;

/**
 * 数据查询Dao
 */
@Mapper
public interface IDataPermissionQueryDao extends BaseDao<DataPermission> {
	
	/**
	 *根据角色ID,用户ID查询特殊数据权限设置
	 * @param rid 角色ID
	 * @param uid 用户ID
	 * @return 变更记录数
	 */
	public List<DataSpecialPermission> getSpecialPermissions(@Param(value = "rid") String rid, @Param(value = "uid") String uid);
	
	/**
	 *根据角色ID,用户ID查询数据权限设置
	 * @param rid 角色ID
	 * @param uid 用户ID
	 * @return 变更记录数
	 */
	public List<DataPermission> getPermissions(@Param(value = "rid") String rid, @Param(value = "uid") String uid);
	
	/**
	 * 根据pid查找对应的数据权限项
	 * @param pid  数据权限ID
	 * @return
	 */
	public List<DataPermissionColumn> getPermissionColumns(@Param("pid") String gid);
	
	/**
	 * 根据SQL查询数据（返回结果必须是字符串数集合）
	 * @param sql
	 * @return
	 */
	List<String> getDataList(@Param("sql") String sql);
	
}
