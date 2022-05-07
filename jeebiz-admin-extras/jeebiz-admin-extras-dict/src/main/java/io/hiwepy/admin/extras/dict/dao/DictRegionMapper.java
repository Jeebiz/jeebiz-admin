package io.hiwepy.admin.extras.dict.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.dict.dao.entities.DictRegionEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1 Mapper 接口
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Mapper
public interface DictRegionMapper extends BaseMapper<DictRegionEntity> {

}
