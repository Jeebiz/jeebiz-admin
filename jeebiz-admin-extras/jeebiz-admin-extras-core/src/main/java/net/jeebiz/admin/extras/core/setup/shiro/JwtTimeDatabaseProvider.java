package net.jeebiz.admin.extras.core.setup.shiro;

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
		try {
			String nowString = getCommonDao().getNow();
			return DateUtils.parseDate(nowString, "yyyy-MM-dd HH:mm:ss").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

}
