/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.login.dao.entities.AuthzThirdpartyUserProfileModel;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 用户详情管理DAO
 * @author hiwepy
 */
@Mapper
public interface AuthzThirdpartyUserProfileMapper extends BaseMapper<AuthzThirdpartyUserProfileModel>{

	/**
	 * 根据手机号查询相同手机号数量
	 * @param phone 手机号码
	 * @return
	 */
	Long getCountByPhone( @Param("phone") String phone, @Param("origin") String origin);

	/**
	 * 根据email查询相同手机号数量
	 * @param email 手机号码
	 * @return
	 */
	Long getCountByEmail( @Param("email") String email, @Param("origin") String origin);

	/**
	 * 根据idcard查询相同身份证号数量
	 * @param idcard 身份证号
	 * @return
	 */
	Long getCountByIdcard( @Param("idcard") String idcard, @Param("origin") String origin);

}
