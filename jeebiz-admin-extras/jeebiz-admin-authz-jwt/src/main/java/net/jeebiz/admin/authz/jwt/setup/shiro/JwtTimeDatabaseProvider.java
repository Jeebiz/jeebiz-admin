package net.jeebiz.admin.authz.jwt.setup.shiro;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;

import com.github.hiwepy.jwt.time.JwtTimeProvider;

import net.jeebiz.admin.extras.core.dao.ICommonDao;

public class JwtTimeDatabaseProvider implements JwtTimeProvider {

	private ICommonDao commonDao;
	
	public JwtTimeDatabaseProvider(ICommonDao commonDao) {
		super();
		this.commonDao = commonDao;
	}

	@Override
	public long now() {
		return getCommonDao().getNow();
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

}
