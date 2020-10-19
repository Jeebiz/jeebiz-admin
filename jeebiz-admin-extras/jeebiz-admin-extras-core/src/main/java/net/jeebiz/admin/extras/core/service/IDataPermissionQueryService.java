/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.core.service;

import java.util.List;

import org.apache.mybatis.dbperms.dto.DataPermission;
import org.apache.mybatis.dbperms.dto.DataPermissionColumn;
import org.apache.mybatis.dbperms.dto.DataSpecialPermission;

import net.jeebiz.boot.api.service.IBaseService;

public interface IDataPermissionQueryService extends IBaseService<DataPermission> {

	/**
	 *根据角色ID,用户ID查询数据权限设置
	 * @param rid 角色ID
	 * @param uid 用户ID
	 * @return 变更记录数
	 */
	public List<DataPermission> getPermissions( String rid, String uid);

	/**
	 *根据角色ID,用户ID查询数据权限设置
	 * @param rid 角色ID
	 * @param uid 用户ID
	 * @return 变更记录数
	 */
	public List<DataSpecialPermission> getSpecialPermissions( String rid, String uid);
	
	/**
	 * 根据pid查找对应的数据权限项
	 * @param pid  数据权限ID
	 * @return
	 */
	public List<DataPermissionColumn> getPermissionColumns( String gid);
	
}
