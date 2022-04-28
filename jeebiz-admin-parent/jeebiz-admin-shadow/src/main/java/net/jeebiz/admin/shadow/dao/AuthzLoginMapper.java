/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.shadow.dao.entities.AuthzLoginModel;
import net.jeebiz.admin.shadow.dao.entities.AuthzLoginStatusModel;
import net.jeebiz.boot.api.dao.BaseMapper;
import net.jeebiz.boot.api.dao.entities.BaseMap;

/**
 * 登录查询Mapper
 * 
 * @author wandl
 */
@Mapper
public interface AuthzLoginMapper extends BaseMapper<AuthzLoginModel> {

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @param password : 密码，可不填
	 * @return 用户账号状态信息
	 */
	AuthzLoginStatusModel getAccountStatus(@Param(value = "account") String account,
										@Param(value = "password") String password);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param account : 用户名
	 * @return 用户账号状态信息
	 */
	AuthzLoginStatusModel getAccountStatusWithoutPwd(@Param(value = "account") String account);

	/**
	 * 根据用户ID和密码查询用户可否登录，角色数量等信息
	 * @param userId : 用户ID
	 * @return 用户账号状态信息
	 */
	AuthzLoginStatusModel getAccountStatusByUid(@Param(value = "userId") String userId);

	/**
	 * 根据用户id和密码查询用户可否登录，角色数量等信息
	 * @param id : 用户名
	 * @return 用户账号状态信息
	 */
    AuthzLoginStatusModel getAccountStatusById(@Param(value = "id") String uid);
    
	/***
	 *  根据用户id和密码查询用户信息
	 * @param account : 用户名
	 * @param password : 密码，可不填
	 * @return 用户登录信息
	 */
	AuthzLoginModel getAccount(@Param(value = "account") String account,
			@Param(value = "password") String password);

	/***
	 * 根据用户id无密码查询用户信息；用于单点登录
	 * @param account : 用户名
	 * @return 用户登录信息
	 */
	AuthzLoginModel getAccountWithoutPwd(@Param(value = "account") String account);

	/**
	 * 根据用户表id查询当前系统对应的用户信息
	 * @param uid 用户表id
	 * @return
	 */
	AuthzLoginModel getAccountById(@Param(value = "id") String id);
	
	/**
	 * 根据用户业务id查询当前系统对应的用户信息
	 * @param ukey 用户业务id
	 * @return
	 */
	AuthzLoginModel getAccountByUcode(@Param(value = "ucode") String ucode);
	
	/**
	 * 根据用户id查询当前系统对应的用户信息
	 * @param uid 用户id
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
