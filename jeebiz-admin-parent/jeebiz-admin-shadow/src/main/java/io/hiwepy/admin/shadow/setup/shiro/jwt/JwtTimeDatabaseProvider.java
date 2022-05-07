package io.hiwepy.admin.shadow.setup.shiro.jwt;

import com.github.hiwepy.jwt.time.JwtTimeProvider;

import io.hiwepy.admin.shadow.dao.CommonMapper;

public class JwtTimeDatabaseProvider implements JwtTimeProvider {

	private CommonMapper commonMapper;
	
	public JwtTimeDatabaseProvider(CommonMapper commonMapper) {
		super();
		this.commonMapper = commonMapper;
	}

	@Override
	public long now() {
		return getCommonMapper().getNow();
	}

	public CommonMapper getCommonMapper() {
		return commonMapper;
	}

}
