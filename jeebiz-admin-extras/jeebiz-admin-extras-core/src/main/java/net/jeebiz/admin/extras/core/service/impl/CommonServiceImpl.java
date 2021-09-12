package net.jeebiz.admin.extras.core.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.extras.core.dao.ICommonDao;
import net.jeebiz.admin.extras.core.service.ICommonService;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;

/**
 *  公共查询Service 实现
 * @author wandl
 */
@Service
public class CommonServiceImpl extends BaseServiceImpl<PairModel, ICommonDao> implements ICommonService {

	@Override
	public Long getNow() {
		return getDao().getNow();
	}

	@Override
	public Long getNowMillis() {
		return getDao().getNowMillis();
	}

	@Override
	public String getNowString(String format) {
		return getDao().getNowString();
	}

}
