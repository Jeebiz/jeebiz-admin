/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.dict.setup.event;

import java.util.List;

import org.springframework.biz.context.event.EnhancedEvent;

import com.google.common.collect.Lists;

import io.hiwepy.boot.api.dao.entities.PairModel;

/**
 */
@SuppressWarnings("serial")
public class DictPairRenewedEvent extends EnhancedEvent<List<PairModel>> {
	
	private final String key;
	
	public DictPairRenewedEvent(Object source, String key, PairModel keyValue) {
		super(source, Lists.newArrayList(keyValue));
		this.key = key;
	}
	
	public DictPairRenewedEvent(Object source, String key, List<PairModel> keyValues) {
		super(source, keyValues);
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
