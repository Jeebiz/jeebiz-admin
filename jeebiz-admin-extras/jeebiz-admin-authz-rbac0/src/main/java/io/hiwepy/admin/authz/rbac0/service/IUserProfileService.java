/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service;

import io.hiwepy.admin.authz.rbac0.dao.entities.UserProfileEntity;
import io.hiwepy.admin.authz.rbac0.web.dto.UserResetDTO;
import io.hiwepy.boot.api.service.IBaseService;


/**
 * 用户详情管理Service接口
 */
public interface IUserProfileService extends IBaseService<UserProfileEntity> {

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

	int resetInfo(String userId, UserResetDTO infoDTO);

}
