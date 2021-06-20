/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.device.setup.listener;

import org.springframework.biz.context.event.EnhancedEvent;

import net.jeebiz.admin.extras.device.web.dto.DeviceActiveEventDTO;

@SuppressWarnings("serial")
public class DeviceActiveEvent extends EnhancedEvent<DeviceActiveEventDTO> {

	public DeviceActiveEvent(Object source, DeviceActiveEventDTO bind) {
		super(source, bind);
	}

}
