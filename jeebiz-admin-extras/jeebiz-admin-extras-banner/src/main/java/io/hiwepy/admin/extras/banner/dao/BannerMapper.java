package io.hiwepy.admin.extras.banner.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.banner.dao.entities.BannerEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface BannerMapper extends BaseMapper<BannerEntity> {
	
}
