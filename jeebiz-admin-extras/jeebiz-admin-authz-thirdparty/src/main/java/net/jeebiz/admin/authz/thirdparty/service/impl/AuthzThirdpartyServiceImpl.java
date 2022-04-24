/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.login.service.impl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.jeebiz.admin.authz.login.dao.AuthzThirdpartyMapper;
import net.jeebiz.admin.authz.login.dao.entities.AuthzThirdpartyModel;
import net.jeebiz.admin.authz.login.service.IAuthzThirdpartyService;
import net.jeebiz.admin.authz.login.setup.ThirdpartyType;
import net.jeebiz.admin.authz.login.setup.provider.ThirdpartyBindingProvider;
import net.jeebiz.admin.authz.login.web.dto.AbstractBindDTO;
import net.jeebiz.admin.authz.login.web.dto.AuthzThirdpartyDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class AuthzThirdpartyServiceImpl extends BaseServiceImpl<AuthzThirdpartyMapper, AuthzThirdpartyModel>
		implements IAuthzThirdpartyService {

	private List<ThirdpartyBindingProvider> bindingProviders;

	@Autowired
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
	public int unbindByUnionid(ThirdpartyType type, String unionid) throws AuthenticationException {
		AuthzThirdpartyModel model = this.getModelByUnionId(type, unionid);
		if(null == model) {
			return 0;
		}
		for (ThirdpartyBindingProvider provider : getBindingProviders()) {
			if(provider.getType().equals(model.getType())) {
				provider.unbind(model);
			}
		}
		int ct = getBaseMapper().deleteById(model.getId());
		return ct;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public int unbindByOpenid(ThirdpartyType type, String openid) throws AuthenticationException {
		AuthzThirdpartyModel model = this.getModelByOpenId(type, openid);
		if(null == model) {
			return 0;
		}
		for (ThirdpartyBindingProvider provider : getBindingProviders()) {
			if(provider.getType().equals(model.getType())) {
				provider.unbind(model);
			}
		}
		int ct = getBaseMapper().deleteById(model.getId());
		return ct;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int unbindByUid(ThirdpartyType type, String uid) throws AuthenticationException {
		AuthzThirdpartyModel model = this.getModelByUid(type, uid);
		if(null == model) {
			return 0;
		}
		for (ThirdpartyBindingProvider provider : getBindingProviders()) {
			if(provider.getType().equals(model.getType())) {
				provider.unbind(model);
			}
		}
		int ct = getBaseMapper().deleteById(model.getId());
		return ct;
	}

	@Override
	public AuthzThirdpartyModel getModelByUnionId(ThirdpartyType type, String unionid) {
		AuthzThirdpartyModel model = getBaseMapper().selectOne(new QueryWrapper<AuthzThirdpartyModel>()
				.eq("t_type", type.name())
				.eq("t_unionid", unionid));
		return model;
	}

	@Override
	public AuthzThirdpartyModel getModelByOpenId(ThirdpartyType type, String openid) {
		AuthzThirdpartyModel model = getBaseMapper().selectOne(new QueryWrapper<AuthzThirdpartyModel>()
				.eq("t_type", type.name())
				.eq("t_openid", openid));
		return model;
	}

	@Override
	public AuthzThirdpartyModel getModelByUid(ThirdpartyType type, String uid) {
		AuthzThirdpartyModel model = getBaseMapper().selectOne(new QueryWrapper<AuthzThirdpartyModel>()
				.eq("t_type", type.name())
				.eq("u_id", uid));
		return model;
	}

	@Override
	public Long getCountByUnionId(String unionid) {
		return getBaseMapper().selectCount(new QueryWrapper<AuthzThirdpartyModel>().eq("t_unionid", unionid));
	}

    @Override
    public Long getCountByOpenId(String openid) {
        return getBaseMapper().selectCount(new QueryWrapper<AuthzThirdpartyModel>().eq("t_openid", openid));
    }

	@Override
	public Long getCountByUid(ThirdpartyType type, String uid) {
		return getBaseMapper().selectCount(new QueryWrapper<AuthzThirdpartyModel>()
				.eq("t_type", type.name())
				.eq("u_id", uid));
	}

	public List<ThirdpartyBindingProvider> getBindingProviders() {
		return bindingProviders;
	}


}
