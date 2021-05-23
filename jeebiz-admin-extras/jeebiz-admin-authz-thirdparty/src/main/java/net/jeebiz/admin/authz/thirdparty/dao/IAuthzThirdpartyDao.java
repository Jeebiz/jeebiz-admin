/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.boot.api.dao.BaseMapper;

/**
 * 第三方账号登录DAO
 */
@Mapper
public interface IAuthzThirdpartyDao extends BaseMapper<AuthzThirdpartyModel>{

	
	/**
	 * 根据Openid查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param uid
	 * @return
	 */
	AuthzThirdpartyModel getModelByUid(@Param("type") String type, @Param("uid") String uid);
	
	/**
	 * 根据UnionId查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param unionid
	 * @return
	 */
	AuthzThirdpartyModel getModelByUnionId(@Param("type") String type, @Param("unionid") String unionid);
	
	/**
	 * 根据Openid查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param openid
	 * @return
	 */
	AuthzThirdpartyModel getModelByOpenId(@Param("type") String type, @Param("openid") String openid);
	
	/**
	 * 根据第三方平台UnionId查询对应的绑定数据
	 * @param unionid
	 * @return
	 */
	int getCountByUnionId(@Param("unionid") String unionid);
	
	/**
	 * 根据第三方平台Openid查询对应的绑定数据
	 * @param openid
	 * @return
	 */
	int getCountByOpenId(@Param("openid") String openid);

	int getCountByUid(@Param("type") String type, @Param("uid") String uid);
		
}
