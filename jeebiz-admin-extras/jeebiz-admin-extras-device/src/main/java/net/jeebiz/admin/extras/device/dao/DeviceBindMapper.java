/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.device.dao.entities.DeviceBindModel;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface DeviceBindMapper extends BaseMapper<DeviceBindModel> {

	/**
	 * @param deviceIMEI
	 * @return
	 */
	DeviceBindModel getBindByDeviceIMEI(@Param("uid") Long uid, @Param("deviceIMEI") String deviceIMEI);

}