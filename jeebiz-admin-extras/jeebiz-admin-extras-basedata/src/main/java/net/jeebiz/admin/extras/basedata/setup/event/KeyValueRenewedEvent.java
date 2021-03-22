/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.basedata.setup.event;

import java.util.List;

import org.springframework.biz.context.event.EnhancedEvent;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.basedata.dao.entities.KeyValueModel;

/**
 */
@SuppressWarnings("serial")
public class KeyValueRenewedEvent extends EnhancedEvent<List<KeyValueModel>> {
	
	public KeyValueRenewedEvent(Object source, KeyValueModel keyValue) {
		super(source, Lists.newArrayList(keyValue));
	}
	
	public KeyValueRenewedEvent(Object source, List<KeyValueModel> keyValues) {
		super(source, keyValues);
	}
	
}
