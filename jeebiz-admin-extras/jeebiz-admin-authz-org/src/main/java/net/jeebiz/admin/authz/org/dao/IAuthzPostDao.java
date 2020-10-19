/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.authz.org.dao.entities.AuthzPostModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IAuthzPostDao extends BaseDao<AuthzPostModel> {

}
