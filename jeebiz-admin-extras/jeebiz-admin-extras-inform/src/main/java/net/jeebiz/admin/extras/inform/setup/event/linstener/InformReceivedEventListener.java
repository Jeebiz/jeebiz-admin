/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.event.linstener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import net.jeebiz.admin.extras.inform.dao.IInformDao;
import net.jeebiz.admin.extras.inform.dao.entities.InformModel;
import net.jeebiz.admin.extras.inform.setup.event.InformReceivedEvent;

@Component
public class InformReceivedEventListener implements ApplicationListener<InformReceivedEvent> {

	@Autowired
    private IInformDao serviceInformDao;
	
	@Override
	public void onApplicationEvent(InformReceivedEvent event) {
		
		for( InformModel model : event.getBind()) {
			// 保存消息通知
			getServiceInformDao().insert(model);
		}
		
	}

	public IInformDao getServiceInformDao() {
		return serviceInformDao;
	}

	public void setServiceInformDao(IInformDao serviceInformDao) {
		this.serviceInformDao = serviceInformDao;
	}
	
}
