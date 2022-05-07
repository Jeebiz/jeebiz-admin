/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.device.setup.listener;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import io.hiwepy.admin.extras.device.dao.DeviceActivateMapper;
import io.hiwepy.admin.extras.device.dao.entities.DeviceActivateModel;
import io.hiwepy.admin.extras.device.web.dto.DeviceActivateEventDTO;

@Component
@Slf4j
public class DeviceActiveEventListener implements ApplicationListener<DeviceActivateEvent> {

    @Autowired
    private DeviceActivateMapper devicedeviceMapper;

    @Override
    public void onApplicationEvent(DeviceActivateEvent event) {
    	try {

    		DeviceActivateEventDTO eventDto = event.getBind();

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
    		// 3、查询是否有对应的设备信息
			Long count = getDevicedeviceMapper().selectCount(queryWrapper);
			if(Objects.isNull(count) || count.intValue() == 0){
				DeviceActivateModel deviceModel = DeviceActivateModel.builder()
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
			            .status(1) // 默认可用
			            .build();
				getDevicedeviceMapper().insert(deviceModel);
			}
		} catch (Exception e) {
			log.error("Device Activate Exception:", e);
		}
    }

    public DeviceActivateMapper getDevicedeviceMapper() {
		return devicedeviceMapper;
	}

}
