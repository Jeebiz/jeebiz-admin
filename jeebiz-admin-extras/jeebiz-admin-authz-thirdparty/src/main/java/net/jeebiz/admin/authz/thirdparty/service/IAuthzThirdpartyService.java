/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.thirdparty.service;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;

import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.dto.AbstractBindDTO;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzThirdpartyDTO;
import net.jeebiz.boot.api.service.IBaseMapperService;

/**
 * @author hiwepy
 */
public interface IAuthzThirdpartyService extends IBaseMapperService<AuthzThirdpartyModel>{

	/**
	 * 添加账号与第三方账号的绑定（新增第三方登录数据）
	 * @param model
	 * @return
	 */
	<T extends AbstractBindDTO> AuthzThirdpartyDTO binding( HttpServletRequest request, T bindDTO) throws AuthenticationException;

	/**
	 * 解除账号与第三方账号的绑定（删除第三方登录数据）
	 * @param unionid
	 * @return
	 */
	int unbindByUnionid(ThirdpartyType type, String unionid) throws AuthenticationException;

	/**
	 * 解除账号与第三方账号的绑定（删除第三方登录数据）
	 * @param openid
	 * @return
	 */
	int unbindByOpenid(ThirdpartyType type, String openid) throws AuthenticationException;

	/**
	 * 解除账号与第三方账号的绑定（删除第三方登录数据）
	 * @param uid
	 * @return
	 */
	int unbindByUid(ThirdpartyType type, String uid) throws AuthenticationException;


	/**
	 * 根据Openid查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param uid
	 * @return
	 */
	AuthzThirdpartyModel getModelByUid(ThirdpartyType type, String uid);

	/**
	 * 根据UnionId查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param unionid
	 * @return
	 */
	AuthzThirdpartyModel getModelByUnionId(ThirdpartyType type, String unionid);

	/**
	 * 根据Openid查询该第三方登录账户
	 * @param type 第三方账号类型
	 * @param openid
	 * @return
	 */
	AuthzThirdpartyModel getModelByOpenId(ThirdpartyType type, String openid);

	/**
	 * 根据第三方平台UnionId查询对应的绑定数据
	 * @param unionid
	 * @return
	 */
	Long getCountByUnionId(String unionid);

	/**
	 * 根据第三方平台Openid查询对应的绑定数据
	 * @param openid
	 * @return
	 */
	Long getCountByOpenId(String openid);

	/**
	 * 根据唯一id编码获取记录数
	 * @param type
	 * @param uid
	 * @return
	 */
	Long getCountByUid(ThirdpartyType type, String uid);



}
