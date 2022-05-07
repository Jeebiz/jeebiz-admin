package io.hiwepy.admin.shadow.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.shadow.dao.CommonMapper;
import io.hiwepy.admin.shadow.service.ICommonService;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;

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
