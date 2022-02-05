package net.jeebiz.admin.shadow.service.impl;

import org.springframework.stereotype.Service;

import net.jeebiz.admin.shadow.dao.CommonMapper;
import net.jeebiz.admin.shadow.service.ICommonService;
import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.BaseServiceImpl;

/**
 *  公共查询Service 实现
 * @author wandl
 */
@Service
public class CommonServiceImpl extends BaseServiceImpl<CommonMapper, PairModel> implements ICommonService {

	@Override
	public Long getNow() {
		return getBaseMapper().getNow();
	}

	@Override
	public Long getNowMillis() {
		return getBaseMapper().getNowMillis();
	}

	@Override
	public String getNowString(String format) {
		return getBaseMapper().getNowString();
	}

}
