/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.shadow.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.authz.rbac0.dao.entities.UserAccountEntity;
import io.hiwepy.boot.api.dao.BaseMapper;
import io.hiwepy.boot.api.dao.entities.BaseMap;

/**
 * 登录查询Mapper
 * @author wandl
 */
@Mapper
public interface AuthMapper extends BaseMapper<UserAccountEntity> {

	/**
	 * 查询用户个人信息
	 * @param userId
	 * @return
	 */
	BaseMap getAuthProfile(@Param(value = "userId") String userId);

}
