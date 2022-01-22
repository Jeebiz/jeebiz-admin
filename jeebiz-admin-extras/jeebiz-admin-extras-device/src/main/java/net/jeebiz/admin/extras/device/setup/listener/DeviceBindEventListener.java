/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.setup.listener;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.device.dao.DeviceActivateMapper;
import net.jeebiz.admin.extras.device.dao.DeviceBindMapper;
import net.jeebiz.admin.extras.device.dao.entities.DeviceActivateModel;
import net.jeebiz.admin.extras.device.dao.entities.DeviceBindModel;
import net.jeebiz.admin.extras.device.web.dto.DeviceBindEventDTO;

@Component
@Slf4j
public class DeviceBindEventListener implements ApplicationListener<DeviceBindEvent> {


    @Autowired
    private DeviceActivateMapper devicedeviceMapper;
    @Autowired
    private DeviceBindMapper deviceBindMapper;

    @Override
    public void onApplicationEvent(DeviceBindEvent event) {
    	
    	try {
			// 1、根据参数查询对应的设备是否已经存在
			DeviceBindEventDTO eventDto = event.getBind();
			QueryWrapper<DeviceActivateModel> queryWrapper = new QueryWrapper<>();
    		// 1、判断是否 Apple 设备
    		if(StringUtils.hasText(eventDto.getIdfa()) ) {
    			// ios 手机的 idfa 原值  > IOS 6+的设备id字段，32位
    			queryWrapper = queryWrapper.eq("DEVICE_IDFA", eventDto.getIdfa());
    		}
    		else if(StringUtils.hasText(eventDto.getOpenudid()) ) {
    			queryWrapper = queryWrapper.eq("DEVICE_OPENUDID", eventDto.getOpenudid());
    		}
    		// 2、判断是否 Android 设备
			else if(StringUtils.hasText(eventDto.getAndroidid()) ) {
    			queryWrapper = queryWrapper.eq("DEVICE_ANDROIDID", eventDto.getAndroidid());
    		}
			else if(StringUtils.hasText(eventDto.getOaid()) ) {
    			queryWrapper = queryWrapper.eq("DEVICE_OAID", eventDto.getOaid());
    		}
    		else if(StringUtils.hasText(eventDto.getImei()) ) {
    			queryWrapper = queryWrapper.eq("DEVICE_IMEI", eventDto.getImei());
    		}
    		// 3、查询是对应的设备信息
			DeviceActivateModel model = getDevicedeviceMapper().selectOne(queryWrapper);
			if(Objects.isNull(model)) {
				model = DeviceActivateModel.builder()
						.appId(eventDto.getAppId())
			            .appChannel(eventDto.getAppChannel())
			            .appVersion(eventDto.getAppVer())
			            .idfa(eventDto.getIdfa())
			            .idfa_md5(StringUtils.hasText(eventDto.getIdfa()) ? DigestUtils.md5DigestAsHex(eventDto.getIdfa().getBytes()) : null)
			            .openudid(eventDto.getOpenudid())
			            .openudid_md5(StringUtils.hasText(eventDto.getOpenudid()) ? DigestUtils.md5DigestAsHex(eventDto.getOpenudid().getBytes()) : null)
			            .androidid(eventDto.getAndroidid())
			            .androidid_md5(StringUtils.hasText(eventDto.getAndroidid()) ? DigestUtils.md5DigestAsHex(eventDto.getAndroidid().getBytes()) : null)
			            .oaid(eventDto.getOaid())
			            .oaid_md5(StringUtils.hasText(eventDto.getOaid()) ? DigestUtils.md5DigestAsHex(eventDto.getOaid().getBytes()) : null)
			            .imei(eventDto.getImei())
			            .imei_md5(StringUtils.hasText(eventDto.getImei()) ? DigestUtils.md5DigestAsHex(eventDto.getImei().getBytes()) : null)
			            .model(eventDto.getModel())
			            .mac(eventDto.getMac())
			            .ua(eventDto.getUa())
			            .ip(eventDto.getIp())
			            .build();
				getDevicedeviceMapper().insert(model);
    		}
			if(Objects.nonNull(model)){
				// 2、查询设备对应的用户绑定数据
				DeviceBindModel bindModel = getDeviceBindMapper().selectOne(new QueryWrapper<DeviceBindModel>()
						.eq("device_id", model.getId())
						.eq("creator", eventDto.getUid()));
				// 2.1、存在则更新客户端信息
				if(Objects.nonNull(bindModel)){
					bindModel.setAppId(eventDto.getAppId());
					bindModel.setAppChannel(eventDto.getAppChannel());
					bindModel.setAppVersion(eventDto.getAppVer());
					getDeviceBindMapper().updateById(bindModel);
				} 
				// 2.2、不存在，则新增记录
				else {
					bindModel = DeviceBindModel.builder()
						 .deviceId(model.getId())
			             .appId(eventDto.getAppId())
			             .appChannel(eventDto.getAppChannel())
			             .appVersion(eventDto.getAppVer())
			             .creator(eventDto.getUid())
			             .build();
					getDeviceBindMapper().insert(bindModel);
				}
			}
		} catch (Exception e) {
			log.error("Device Bind Exception:", e);
		}
    }
    
    public DeviceActivateMapper getDevicedeviceMapper() {
		return devicedeviceMapper;
	}
    
    public DeviceBindMapper getDeviceBindMapper() {
		return deviceBindMapper;
	}
    
}
