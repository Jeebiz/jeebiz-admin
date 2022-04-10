/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.rbac0.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.jeebiz.admin.authz.rbac0.dao.AuthzUserProfileMapper;
import net.jeebiz.admin.authz.rbac0.dao.entities.AuthzUserProfileEntity;
import net.jeebiz.admin.authz.rbac0.service.IAuthzUserProfileService;
import net.jeebiz.admin.authz.rbac0.web.dto.AuthzUserResetDTO;
import net.jeebiz.boot.api.service.BaseServiceImpl;

@Service
public class AuthzUserProfileServiceImpl extends BaseServiceImpl<AuthzUserProfileMapper, AuthzUserProfileEntity> implements IAuthzUserProfileService {

	@Override
	public AuthzUserProfileEntity getProfile(String uid) {
		return getBaseMapper().selectById(uid);
	}

	@Override
	public int getCountByPhone(String phone, String origin) {
		return getBaseMapper().getCountByPhone(phone, origin);
	}

	@Override
	public int getCountByEmail(String email, String origin) {
		return getBaseMapper().getCountByEmail(email, origin);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int resetInfo(String userId, AuthzUserResetDTO infoDTO) {
		AuthzUserProfileEntity profileModel = AuthzUserProfileEntity.builder()
			.avatar(infoDTO.getAvatar())
			.nickname(infoDTO.getNickname())
			.build();
		return getBaseMapper().update(profileModel, new QueryWrapper<AuthzUserProfileEntity>().eq("u_id", userId));
	}
}
