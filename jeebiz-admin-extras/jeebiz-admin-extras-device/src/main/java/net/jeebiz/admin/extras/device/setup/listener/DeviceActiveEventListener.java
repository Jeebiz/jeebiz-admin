/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.setup.listener;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.device.dao.IDeviceActivateDao;
import net.jeebiz.admin.extras.device.dao.entities.DeviceActivateModel;
import net.jeebiz.admin.extras.device.web.dto.DeviceActiveEventDTO;

@Component
public class DeviceActiveEventListener implements ApplicationListener<DeviceActiveEvent> {

    @Autowired
    private IDeviceActivateDao deviceActivationDao;
    
    @Override
    public void onApplicationEvent(DeviceActiveEvent event) {
    	DeviceActiveEventDTO bind = event.getBind();
        DeviceActivateModel model = getDeviceActivationDao().getActivatedByDeviceIMEI(bind.getDeviceIMEI());
        if(Objects.isNull(model)){
        	DeviceActivateModel activationModel = DeviceActivateModel.builder()
                    .appId(bind.getAppId())
                    .appChannel(bind.getAppChannel())
                    .appVersion(bind.getAppVersion())
                    .deviceImei(bind.getDeviceIMEI())
                    .deviceModel(bind.getDeviceModel())
                    .build();
        	getDeviceActivationDao().insert(activationModel);
        }
    }
    
    public IDeviceActivateDao getDeviceActivationDao() {
		return deviceActivationDao;
	}
    
}
