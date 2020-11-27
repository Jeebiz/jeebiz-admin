/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.setup.provider;

import org.apache.shiro.authc.AuthenticationException;

import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.dto.AbstractBindDTO;

public interface ThirdpartyBindingProvider<T extends AbstractBindDTO> {

	/**
	 * Provider Type
	 * @return
	 */
	ThirdpartyType getType();
	
	/**
	 * 登录绑定
	 * @param bindDTO 绑定信息
	 * @return
	 */
	AuthzThirdpartyModel binding(T bindDTO) throws AuthenticationException;
	
	/**
	 * 解除绑定
	 * @param id 唯一主键
	 * @return
	 */
	int unbind(AuthzThirdpartyModel model) throws AuthenticationException;
	
}
