/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.authz.feature.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.authz.feature.dao.entities.FeatureOptEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface FeatureOptMapper extends BaseMapper<FeatureOptEntity> {

	FeatureOptEntity getFeatureOpt(@Param(value = "id") String id);

	List<FeatureOptEntity> getFeatureOpts();

	List<FeatureOptEntity> getFeatureOptList(@Param(value = "featureId") String featureId, @Param(value = "visible") String visible);

	int getOptCountByName(@Param(value = "name") String name, @Param(value = "featureId") String featureId, @Param(value = "optId") String optId);

	int deleteByParent(@Param(value = "id") Serializable id);

}
