/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.device.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.extras.device.dao.entities.DeviceBindModel;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface DeviceBindMapper extends BaseMapper<DeviceBindModel> {

	/**
	 * @param deviceIMEI
	 * @return
	 */
	DeviceBindModel getBindByDeviceIMEI(@Param("uid") Long uid, @Param("deviceIMEI") String deviceIMEI);

}