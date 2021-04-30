/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.device.dao.entities.DeviceActivateModel;
import net.jeebiz.boot.api.dao.BaseMapper;

@Mapper
public interface IDeviceActivateDao extends BaseMapper<DeviceActivateModel> {

	/**
	 * 通过设备唯一标识查询主键id
	 * 
	 * @param deviceIMEI
	 * @return
	 */
	DeviceActivateModel getActivatedByDeviceIMEI(@Param("deviceIMEI") String deviceIMEI);

}