/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.PostMapper;
import io.hiwepy.admin.authz.org.dao.entities.PostEntity;
import io.hiwepy.admin.authz.org.service.IPostService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class PostServiceImpl extends BaseServiceImpl<PostMapper, PostEntity> implements IPostService{

}
