/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.device.setup.listener;

import org.springframework.biz.context.event.EnhancedEvent;

import io.hiwepy.admin.extras.device.web.dto.DeviceBindEventDTO;

@SuppressWarnings("serial")
public class DeviceBindEvent extends EnhancedEvent<DeviceBindEventDTO> {

	public DeviceBindEvent(Object source, DeviceBindEventDTO bind) {
		super(source, bind);
	}

}
