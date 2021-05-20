/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.authz.org.dao.entities.AuthzStaffModel;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface IAuthzStaffDao extends BaseMapper<AuthzStaffModel> {

}
