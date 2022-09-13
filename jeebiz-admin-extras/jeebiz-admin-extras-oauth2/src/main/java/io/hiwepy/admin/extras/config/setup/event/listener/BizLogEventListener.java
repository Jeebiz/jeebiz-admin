/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.logs.setup.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.hiwepy.admin.extras.logs.setup.event.BizLogEvent;

@Component
public class BizLogEventListener implements ApplicationListener<BizLogEvent> {


	@Override
	public void onApplicationEvent(BizLogEvent event) {

	}

}
