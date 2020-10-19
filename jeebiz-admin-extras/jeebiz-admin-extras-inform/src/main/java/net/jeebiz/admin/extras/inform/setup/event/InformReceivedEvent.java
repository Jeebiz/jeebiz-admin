/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.event;

import java.util.List;

import org.springframework.biz.context.event.EnhancedEvent;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;

/**
 */
@SuppressWarnings("serial")
public class InformReceivedEvent extends EnhancedEvent<List<InformRecordModel>> {
	
	public InformReceivedEvent(Object source, InformRecordModel inform) {
		super(source, Lists.newArrayList(inform));
	}
	
	public InformReceivedEvent(Object source, List<InformRecordModel> informs) {
		super(source, informs);
	}
	
}
