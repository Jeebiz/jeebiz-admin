package io.hiwepy.admin.extras.banner.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKey;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hiwepy.admin.extras.banner.dao.BannerMapper;
import io.hiwepy.admin.extras.banner.dao.entities.BannerEntity;
import io.hiwepy.admin.extras.banner.service.IBannerService;
import io.hiwepy.admin.extras.banner.setup.BannerType;
import io.hiwepy.admin.extras.banner.web.dto.BannerDTO;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class BannerServiceImpl extends BaseServiceImpl<BannerMapper, BannerEntity>
		implements IBannerService {

	@Autowired
	protected RedisOperationTemplate redisOperation;

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public boolean save(BannerEntity entity) {
		boolean rt = super.save(entity);
		if(rt){
			String bannerListKey = this.getBannerListKey( entity.getAppId(), entity.getAppChannel(), entity.getRegion(), entity.getLanguage(), entity.getType());
			redisOperation.hSet(bannerListKey, entity.getId(), entity);
		}
		return rt;
	}

	@Override
	public boolean removeById(Serializable id) {
		BannerEntity entity = super.getById(id);
		boolean rt = super.removeById(id);
		if(rt){
			String bannerListKey = this.getBannerListKey( entity.getAppId(), entity.getAppChannel(), entity.getRegion(), entity.getLanguage(), entity.getType());
			redisOperation.hDel(bannerListKey, entity.getId());
		}
		return rt;
	}

	@Override
	public boolean updateById(BannerEntity entity) {
		BannerEntity oldEntity = super.getById(entity.getId());
		boolean rt = super.updateById(entity);
		if(rt){
			String oldBannerListKey = this.getBannerListKey( oldEntity.getAppId(), oldEntity.getAppChannel(), oldEntity.getRegion(), oldEntity.getLanguage(), oldEntity.getType());
			String bannerListKey = this.getBannerListKey( entity.getAppId(), entity.getAppChannel(), entity.getRegion(), entity.getLanguage(), entity.getType());
			if(!StringUtils.equalsIgnoreCase(oldBannerListKey, bannerListKey)){
				redisOperation.hDel(oldBannerListKey, entity.getId());
				redisOperation.hSet(bannerListKey, entity.getId(), entity);
			} else if(entity.getStatus() == 0){
				redisOperation.hDel(bannerListKey, entity.getId());
			} else{
				redisOperation.hSet(bannerListKey, entity.getId(), entity);
			}
		}
		return rt;
	}

	@Override
	public List<BannerDTO> getBannerList(String appId, String appChannel, String region, String language, Integer type) {
		// 1、轮播图配置
		List<BannerDTO> bannerList = new ArrayList<>();
		String bannerListKey = this.getBannerListKey( appId, appChannel, region, language, type);
		Map<String, Object> bannerConfig = redisOperation.hmGet(bannerListKey);
		if (Objects.nonNull(bannerConfig)) {
			bannerList = bannerConfig.values().stream()
					//.map(obj -> JSON.parseObject((String) obj, BannerDTO.class))
					.map(obj -> getBeanMapper().map(obj, BannerDTO.class))
					.sorted(Comparator.comparing(BannerDTO::getPriority))
					.collect(Collectors.toList());
		}
		return bannerList;
	}

	protected String getBannerListKey(String appId, String appChannel, String region, String language, Integer type){
		String uniqueKey = new StringJoiner(RedisKey.DELIMITER)
				.add(appId)
				.add(appChannel)
				.add(region)
				.add(language).toString();
		return BizRedisKey.BANNER_LIST.getKey(uniqueKey, BannerType.getByCode(type).getRedisKey());
	}


}
