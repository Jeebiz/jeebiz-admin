/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dict.setup.event;

import java.util.List;

import org.springframework.biz.context.event.EnhancedEvent;

import com.google.common.collect.Lists;

import net.jeebiz.boot.api.dao.entities.PairModel;

/**
 */
@SuppressWarnings("serial")
public class KeyValueRenewedEvent extends EnhancedEvent<List<PairModel>> {
	
	private final String key;
	
	public KeyValueRenewedEvent(Object source, String key, PairModel keyValue) {
		super(source, Lists.newArrayList(keyValue));
		this.key = key;
	}
	
	public KeyValueRenewedEvent(Object source, String key, List<PairModel> keyValues) {
		super(source, keyValues);
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
