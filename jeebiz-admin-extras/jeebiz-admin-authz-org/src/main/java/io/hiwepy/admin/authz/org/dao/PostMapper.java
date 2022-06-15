/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.dao;

import org.apache.ibatis.annotations.Mapper;

import io.hiwepy.admin.authz.org.dao.entities.PostModel;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface PostMapper extends BaseMapper<PostModel> {

}