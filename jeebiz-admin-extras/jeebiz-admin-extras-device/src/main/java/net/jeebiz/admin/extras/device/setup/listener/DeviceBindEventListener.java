/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.setup.listener;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.device.dao.ideviceActivateDao;
import net.jeebiz.admin.extras.device.dao.ideviceBindDao;
import net.jeebiz.admin.extras.device.dao.entities.DeviceActivateModel;
import net.jeebiz.admin.extras.device.dao.entities.DeviceBindModel;
import net.jeebiz.admin.extras.device.web.dto.DeviceBindEventDTO;

@Component
@Slf4j
public class DeviceBindEventListener implements ApplicationListener<DeviceBindEvent> {


    @Autowired
    private ideviceActivateDao deviceActivationDao;
    @Autowired
    private ideviceBindDao deviceBindDao;

    @Override
    public void onApplicationEvent(DeviceBindEvent event) {
    	
    	try {
			// 1、根据参数查询对应的设备是否已经存在
			DeviceBindEventDTO bind = event.getBind();
			DeviceActivateModel model = getDeviceActivationDao().getActivatedByDeviceIMEI(bind.getDeviceIMEI());
			if(Objects.nonNull(model)){
				// 2、查询设备对应的用户绑定数据
				DeviceBindModel bindModel = getDeviceBindDao().selectOne(new QueryWrapper<DeviceBindModel>()
						.eq("ACTIVATEd_id", model.getId())
						.eq("CREATOR", bind.getUid()));
				// 2.1、存在则更新客户端信息
				if(Objects.nonNull(bindModel)){
					bindModel.setAppId(bind.getAppId());
					bindModel.setAppChannel(bind.getAppChannel());
					bindModel.setAppVersion(bind.getAppVersion());
					getDeviceBindDao().updateById(bindModel);
				} 
				// 2.2、不存在，则新增记录
				else {
					bindModel = DeviceBindModel.builder()
						 .activatedId(model.getId())
			             .appId(bind.getAppId())
			             .appChannel(bind.getAppChannel())
			             .appVersion(bind.getAppVersion())
			             .creator(bind.getUid())
			             .build();
					getDeviceBindDao().insert(bindModel);
				}
			}
    	} catch (Exception e) {
			log.error("Device Bind Exception:", e);
		}
    }
    
    public ideviceActivateDao getDeviceActivationDao() {
		return deviceActivationDao;
	}
    
    public ideviceBindDao getDeviceBindDao() {
		return deviceBindDao;
	}
    
}
