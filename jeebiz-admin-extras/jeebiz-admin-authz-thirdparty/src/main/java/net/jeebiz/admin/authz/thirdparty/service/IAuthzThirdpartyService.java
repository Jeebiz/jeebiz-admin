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
import net.jeebiz.boot.api.service.IBaseService;

/**
 * @author hiwepy
 */
public interface IAuthzThirdpartyService extends IBaseService<AuthzThirdpartyModel>{
    
	/**
	 * 添加账号与第三方账号的绑定（新增第三方登录数据）
	 * @param model
	 * @return
	 */
	<T extends AbstractBindDTO> AuthzThirdpartyDTO binding( HttpServletRequest request, T bindDTO) throws AuthenticationException;
	
	/**
	 * 解除账号与第三方账号的绑定（删除第三方登录数据）
	 * @param openid
	 * @return
	 */
	int unbind(ThirdpartyType type, String openid) throws AuthenticationException;
	
	/**
	 * 解除账号与第三方账号的绑定（删除第三方登录数据）
	 * @param uid
	 * @return
	 */
	int unbindByUid(ThirdpartyType type, String uid) throws AuthenticationException;
	
	/**
	 * 根据第三方平台OpenID查询对应的绑定数据
	 * @param openid
	 * @return
	 */
	int getCountByOpenId(String openid);

	/**
	 * 根据唯一ID编码获取记录数
	 * @param name
	 * @return
	 */
	public int getCountByUid(ThirdpartyType type, String uid);
	
}
