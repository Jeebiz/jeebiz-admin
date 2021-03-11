/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.service.impl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.jeebiz.admin.authz.thirdparty.dao.IAuthzThirdpartyDao;
import net.jeebiz.admin.authz.thirdparty.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.thirdparty.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.thirdparty.setup.ThirdpartyType;
import net.jeebiz.admin.authz.thirdparty.setup.provider.ThirdpartyBindingProvider;
import net.jeebiz.admin.authz.thirdparty.web.dto.AbstractBindDTO;
import net.jeebiz.admin.authz.thirdparty.web.dto.AuthzThirdpartyDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class AuthzThirdpartyServiceImpl extends BaseServiceImpl<AuthzThirdpartyModel, IAuthzThirdpartyDao>
		implements IAuthzThirdpartyService {
	
	private List<ThirdpartyBindingProvider> bindingProviders;
	
	public AuthzThirdpartyServiceImpl(List<ThirdpartyBindingProvider> bindingProviders) {
		this.bindingProviders = bindingProviders;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <T extends AbstractBindDTO> AuthzThirdpartyDTO binding(HttpServletRequest request, T bindDTO) throws AuthenticationException {
		for (ThirdpartyBindingProvider provider : getBindingProviders()) {
			if(provider.getType().equals(bindDTO.getType())) {
				AuthzThirdpartyModel model = provider.binding(request, bindDTO);
				return getBeanMapper().map(model, AuthzThirdpartyDTO.class);
			}
		}
		return null;
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int unbind(ThirdpartyType type, String openid) throws AuthenticationException {
		AuthzThirdpartyModel model = getDao().getModelByOpenId(type.name(), openid);
		if(null == model) {
			return 0;
		}
		for (ThirdpartyBindingProvider provider : getBindingProviders()) {
			if(provider.getType().equals(model.getType())) {
				provider.unbind(model);
			}
		}
		int ct = getDao().delete(model.getId());
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int unbindByUid(ThirdpartyType type, String uid) throws AuthenticationException {
		AuthzThirdpartyModel model = getDao().getModelByUid(type.name(), uid);
		if(null == model) {
			return 0;
		}
		for (ThirdpartyBindingProvider provider : getBindingProviders()) {
			if(provider.getType().equals(model.getType())) {
				provider.unbind(model);
			}
		}
		int ct = getDao().delete(model.getId());
		return ct;
	}	
	
    @Override
    public int getCountByOpenId(String openid) {
        return dao.getCountByOpenId(openid);
    }

	@Override
	public int getCountByUid(ThirdpartyType type, String uid) {
		return getDao().getCountByUid(type.name(), uid);
	}
	 
	public List<ThirdpartyBindingProvider> getBindingProviders() {
		return bindingProviders;
	}
	
}
