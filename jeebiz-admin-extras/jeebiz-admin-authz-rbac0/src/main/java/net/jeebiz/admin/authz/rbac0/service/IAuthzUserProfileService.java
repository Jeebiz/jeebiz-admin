/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service;

import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserProfileEntity;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserResetDTO;
import net.jeebiz.boot.api.service.IBaseService;


/**
 * 用户详情管理Service接口
 */
public interface IAuthzUserProfileService extends IBaseService<AuthzUserProfileEntity> {

	/**
	 * 根据用户Id查询用户信息
	 * @param uid
	 * @return
	 */
	AuthzUserProfileEntity getProfile(String uid);

	/**
	 * 根据手机号查询相同手机号数量
	 * @param phone 手机号码
	 * @return
	 */
	int getCountByPhone( String phone, String origin);

	/**
	 * 根据邮箱查询相同手机号数量
	 * @param email 手机号码
	 * @return
	 */
	int getCountByEmail(String email, String origin);

	int resetInfo(String userId, AuthzUserResetDTO infoDTO);

}
