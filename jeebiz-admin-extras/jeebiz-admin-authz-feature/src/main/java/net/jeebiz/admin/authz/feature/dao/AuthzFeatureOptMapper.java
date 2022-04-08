/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.authz.feature.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.authz.feature.dao.entities.AuthzFeatureOptEntity;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface AuthzFeatureOptMapper extends BaseMapper<AuthzFeatureOptEntity> {

	AuthzFeatureOptEntity getFeatureOpt(@Param(value = "id") String id);

	List<AuthzFeatureOptEntity> getFeatureOpts();

	List<AuthzFeatureOptEntity> getFeatureOptList(@Param(value = "featureId") String featureId, @Param(value = "visible") String visible);

	int getOptCountByName(@Param(value = "name") String name, @Param(value = "featureId") String featureId, @Param(value = "optId") String optId);

	int deleteByParent(@Param(value = "id") Serializable id);

}
