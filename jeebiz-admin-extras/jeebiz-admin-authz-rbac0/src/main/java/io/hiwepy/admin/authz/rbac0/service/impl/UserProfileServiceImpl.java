/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.rbac0.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.hiwepy.admin.authz.rbac0.dao.UserProfileMapper;
import io.hiwepy.admin.authz.rbac0.dao.entities.UserProfileEntity;
import io.hiwepy.admin.authz.rbac0.service.IUserProfileService;
import io.hiwepy.admin.authz.rbac0.web.dto.UserResetDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfileMapper, UserProfileEntity> implements IUserProfileService {

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
	public int resetInfo(String userId, UserResetDTO infoDTO) {
		UserProfileEntity profileModel = UserProfileEntity.builder()
			.avatar(infoDTO.getAvatar())
			.nickname(infoDTO.getNickname())
			.build();
		return getBaseMapper().update(profileModel, new QueryWrapper<UserProfileEntity>().eq("u_id", userId));
	}
}