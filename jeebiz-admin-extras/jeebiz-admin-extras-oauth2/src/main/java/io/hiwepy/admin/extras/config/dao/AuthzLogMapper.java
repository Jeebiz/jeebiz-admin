/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.logs.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.extras.logs.dao.entities.AuthzLogEntity;
import io.hiwepy.boot.api.dao.BaseMapper;

/**
 *
 */
@Mapper
public interface AuthzLogMapper extends BaseMapper<AuthzLogEntity>{


}
