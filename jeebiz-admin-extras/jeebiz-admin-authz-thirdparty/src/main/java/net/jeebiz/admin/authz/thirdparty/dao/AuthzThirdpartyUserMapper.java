/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.login.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.authz.login.dao.entities.AuthzThirdpartyUserModel;
import net.jeebiz.boot.api.dao.BaseMapper;
/**
 * 第三方账号登录DAO
 */
@Mapper
public interface AuthzThirdpartyUserMapper extends BaseMapper<AuthzThirdpartyUserModel>{
	
}
