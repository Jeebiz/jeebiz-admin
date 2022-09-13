/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.authz.org.service.impl;

import org.springframework.stereotype.Service;

import io.hiwepy.admin.authz.org.dao.StaffMapper;
import io.hiwepy.admin.authz.org.dao.entities.StaffEntity;
import io.hiwepy.admin.authz.org.service.IStaffService;
import io.hiwepy.boot.api.service.BaseServiceImpl;

@Service
public class StaffServiceImpl extends BaseServiceImpl<StaffMapper, StaffEntity> implements IStaffService{
	
}
