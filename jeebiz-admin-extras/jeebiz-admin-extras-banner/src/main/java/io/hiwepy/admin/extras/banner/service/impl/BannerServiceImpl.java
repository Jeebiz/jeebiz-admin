package io.hiwepy.admin.extras.banner.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import io.hiwepy.admin.extras.banner.web.dto.BannerExtendProperty;
import org.apache.commons.collections.CollectionUtils;
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
		// 先在数据库中进行保存
		boolean rt = super.save(entity);
		// 保存成功，则在缓存中保存
		if(rt && Objects.isNull(entity.getStatus()) && entity.getStatus() == 1){
			String bannerListKey = this.getBannerListKey( entity.getAppId(), entity.getAppChannel(), entity.getRegion(), entity.getLanguage(), entity.getType());
			redisOperation.hSet(bannerListKey, entity.getId(), entity);
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public boolean removeById(Serializable id) {
		// 获取要被删除的实体类
		BannerEntity entity = super.getById(id);
		// 先从数据库删除
		boolean rt = super.removeById(id);
		// 如果删除成功，则去缓存里删除
		if(rt){
			String bannerListKey = this.getBannerListKey( entity.getAppId(), entity.getAppChannel(), entity.getRegion(), entity.getLanguage(), entity.getType());
			redisOperation.hDel(bannerListKey, entity.getId());
		}
		return rt;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public boolean updateById(BannerEntity entity) {
		// 获取要被更新的实体类
		BannerEntity oldEntity = super.getById(entity.getId());
		// 先将数据库的Banner对象进行更新
		boolean rt = super.updateById(entity);
		// 更新成功，则更新缓存
		if(rt){
			String oldBannerListKey = this.getBannerListKey( oldEntity.getAppId(), oldEntity.getAppChannel(), oldEntity.getRegion(), oldEntity.getLanguage(), oldEntity.getType());
			String bannerListKey = this.getBannerListKey( entity.getAppId(), entity.getAppChannel(), entity.getRegion(), entity.getLanguage(), entity.getType());
			if(!StringUtils.equalsIgnoreCase(oldBannerListKey, bannerListKey)){
				redisOperation.hDel(oldBannerListKey, entity.getId());
				redisOperation.hSet(bannerListKey, entity.getId(), entity);
			} else if(Objects.isNull(entity.getStatus()) || entity.getStatus() == 0){
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
		if (Objects.isNull(bannerConfig)) {
			return bannerList;
		}
		List<BannerEntity> bannerEntityList = bannerConfig.values().stream().map(o -> JSONObject.parseObject(JSONObject.toJSONString(o), BannerEntity.class)).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(bannerEntityList)) {
			bannerList = bannerEntityList.stream()
					//.map(obj -> JSON.parseObject((String) obj, BannerDTO.class))
					.map(obj -> {
						BannerDTO dto = getBeanMapper().map(obj, BannerDTO.class);
						BannerExtendProperty extendProperty = JSONObject.parseObject(obj.getExtend(), BannerExtendProperty.class);
						dto.setIntervalTime(extendProperty.getIntervalTime());
						return dto;
					}).filter(bannerDTO -> {
						LocalDateTime nowTime = LocalDateTime.now();
						if(Objects.nonNull(bannerDTO.getOpenTime())){
//							LocalDateTime openTime = LocalDateTime.parse(bannerDTO.getOpenTime(), DateTimeFormatter.ofPattern(DateFormats.DATE_LONGFORMAT));
							if(nowTime.isBefore(bannerDTO.getOpenTime())){
								return false;
							}
						}
						if(Objects.nonNull(bannerDTO.getCloseTime())){
//							LocalDateTime closeTime = LocalDateTime.parse(bannerDTO.getCloseTime(), DateTimeFormatter.ofPattern(DateFormats.DATE_LONGFORMAT));
							if(nowTime.isAfter(bannerDTO.getCloseTime())){
								return false;
							}
						}
						return Objects.nonNull(bannerDTO.getStatus()) && bannerDTO.getStatus() == 1;
					})
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
