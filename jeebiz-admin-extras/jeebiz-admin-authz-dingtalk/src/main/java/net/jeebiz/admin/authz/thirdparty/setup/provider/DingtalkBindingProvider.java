/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.setup.provider;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.authz.thirdparty.dao.IAuthzThirdpartyDao;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzDingtalkBindDTO;

@Component
public class DingtalkBindingProvider implements ThirdpartyBindingProvider<AuthzDingtalkBindDTO> {

	@Autowired
	private IAuthzThirdpartyDao authzThirdpartyDao;
	
	@Override
	public ThirdpartyType getType() {
		return ThirdpartyType.WXMA;
	}
	
	@Override
	public AuthzThirdpartyModel binding(HttpServletRequest request, AuthzDingtalkBindDTO bindDTO) throws AuthenticationException {
		
		try {
			
			return null;
		} catch (Exception e) {
			throw new AuthenticationException("微信信息获取异常", e);
		}
	}

	@Override
	public int unbind(AuthzThirdpartyModel model) throws AuthenticationException {
		return 0;
	}
	
	public IAuthzThirdpartyDao getAuthzThirdpartyDao() {
		return authzThirdpartyDao;
	}

}
