/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserProfileEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 用户详情管理DAO
 * @author hiwepy
 */
@Mapper
public interface AuthzUserProfileMapper extends BaseMapper<AuthzUserProfileEntity>{

	/**
	 * 根据手机号查询相同手机号数量
	 * @param phone 手机号码
	 * @return
	 */
	int getCountByPhone( @Param("phone") String phone, @Param("origin") String origin);

	/**
	 * 根据email查询相同手机号数量
	 * @param email 手机号码
	 * @return
	 */
	int getCountByEmail( @Param("email") String email, @Param("origin") String origin);

	/**
	 * 根据idcard查询相同身份证号数量
	 * @param idcard 身份证号
	 * @return
	 */
	int getCountByIdcard( @Param("idcard") String idcard, @Param("origin") String origin);

}
