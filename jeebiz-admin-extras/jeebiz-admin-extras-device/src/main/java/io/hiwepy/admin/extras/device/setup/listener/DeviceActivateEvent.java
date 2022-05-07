/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.device.setup.listener;

import org.springframework.biz.context.event.EnhancedEvent;

import io.hiwepy.admin.extras.device.web.dto.DeviceActivateEventDTO;

@SuppressWarnings("serial")
public class DeviceActivateEvent extends EnhancedEvent<DeviceActivateEventDTO> {

	public DeviceActivateEvent(Object source, DeviceActivateEventDTO bind) {
		super(source, bind);
	}

}