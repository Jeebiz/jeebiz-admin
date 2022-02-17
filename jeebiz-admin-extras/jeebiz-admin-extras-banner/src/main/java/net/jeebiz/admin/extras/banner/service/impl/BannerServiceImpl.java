package net.jeebiz.admin.extras.banner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import net.jeebiz.admin.api.RedisKeyConstant;
import net.jeebiz.admin.extras.banner.dao.BannerMapper;
import net.jeebiz.admin.extras.banner.dao.entities.BannerEntity;
import net.jeebiz.admin.extras.banner.service.IBannerService;
import net.jeebiz.boot.api.service.BaseServiceImpl;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;

@Service
public class BannerServiceImpl extends BaseServiceImpl<BannerMapper, BannerEntity>
		implements IBannerService {
	
	@Autowired
	protected RedisOperationTemplate redisOperationTemplate;
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public boolean save(BannerEntity BannerModel) {
		int rt = getBaseMapper().insert(BannerModel);
		if (rt > 0){
			getRedisOperationTemplate().hSet(RedisKeyConstant.SET_APPS, BannerModel.getAppKey(), BannerModel.getAppSecret());
		}
		return SqlHelper.retBool(rt);
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}
	
}
