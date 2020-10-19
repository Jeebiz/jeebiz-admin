/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.boot.api.dao.BaseDao;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyUserModel;
/**
 * 第三方账号登录DAO
 * @author hiwepy
 */
@Mapper
public interface IAuthzThirdpartyDao extends BaseDao<AuthzThirdpartyModel>{
	
	/**
	 * 根据OpenID查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param uid
	 * @return
	 */
	AuthzThirdpartyModel getModelByUid(@Param("type") String type, @Param("uid") String uid);
	
	/**
	 * 根据OpenID查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param openid
	 * @return
	 */
	AuthzThirdpartyModel getModelByOpenId(@Param("type") String type, @Param("openid") String openid);
	
	/**
	 * 根据第三方平台OpenID查询对应的绑定数据
	 * @param openid
	 * @return
	 */
	int getCountByOpenId(@Param("openid") String openid);

	int getCountByUid(@Param("type") String type, @Param("uid") String uid);
	
	/**
	 * 增加用户记录
	 * @param model
	 * @return
	 */
	public int insertUser(AuthzThirdpartyUserModel model);
	
}
