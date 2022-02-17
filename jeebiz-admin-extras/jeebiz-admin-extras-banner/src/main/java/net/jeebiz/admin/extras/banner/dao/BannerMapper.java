package net.jeebiz.admin.extras.banner.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.banner.dao.entities.BannerEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface BannerMapper extends BaseMapper<BannerEntity> {
	
}
