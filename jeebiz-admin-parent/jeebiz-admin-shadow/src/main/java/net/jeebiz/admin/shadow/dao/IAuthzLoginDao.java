/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginStatusModel;
import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.boot.api.dao.entities.BaseMap;

/**
 * 登录查询Dao
 * 
 * @author wandl
 */
@Mapper
public interface IAuthzLoginDao extends BaseDao<AuthzLoginModel> {

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param username : 用户名
	 * @param password : 密码，可不填
	 * @return 用户账号状态信息
	 */
	AuthzLoginStatusModel getAccountStatus(@Param(value = "username") String username,
			@Param(value = "password") String password);
	
	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param username : 用户名
	 * @return 用户账号状态信息
	 */
	AuthzLoginStatusModel getAccountStatusWithoutPwd(@Param(value = "username") String username);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param username : 用户名
	 * @return 用户账号状态信息
	 */
    AuthzLoginStatusModel getAccountStatusByUid(@Param(value = "uid") String uid);
    
	/***
	 *  根据用户ID和密码查询用户信息
	 * @param username : 用户名
	 * @param password : 密码，可不填
	 * @return 用户登录信息
	 */
	AuthzLoginModel getAccount(@Param(value = "username") String username,
			@Param(value = "password") String password);

	/***
	 * 根据用户ID无密码查询用户信息；用于单点登录
	 * @param username : 用户名
	 * @return 用户登录信息
	 */
	AuthzLoginModel getAccountWithoutPwd(@Param(value = "username") String username);

	/**
	 * 根据用户业务ID查询当前系统对应的用户信息
	 * @param ukey 用户业务ID
	 * @return
	 */
	AuthzLoginModel getAccountByUkey(@Param(value = "ukey") String ukey);
	
	/**
	 * 根据用户ID查询当前系统对应的用户信息
	 * @param uid 用户ID
	 * @return
	 */
	AuthzLoginModel getAccountByUid(@Param(value = "uid") String uid);

	/**
	 * 查询用户个人信息
	 * @param userid
	 * @return
	 */
	public BaseMap getAccountProfile(@Param(value = "userid") String userid);

}
