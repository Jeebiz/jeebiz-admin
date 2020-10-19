/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.event.linstener;

import java.util.List;

import org.springframework.context.ApplicationListener;

import net.jeebiz.admin.extras.inform.dao.entities.InformRecordModel;
import net.jeebiz.admin.extras.inform.setup.event.InformReceivedEvent;
import net.jeebiz.admin.extras.inform.setup.provider.InformOutputProvider;

@SuppressWarnings({"unchecked","rawtypes"})
public class InformReceivedEventListener implements ApplicationListener<InformReceivedEvent> {
	
	private List<InformOutputProvider> outputProviders;
	
	public InformReceivedEventListener(List<InformOutputProvider> outputProviders) {
		this.outputProviders = outputProviders;
	}
	
	@Override
	public void onApplicationEvent(InformReceivedEvent event) {
		for( InformRecordModel model : event.getBind()) {
			for (InformOutputProvider provider : getOutputProviders()) {
				if(provider.getProvider().equals(model.getProvider())) {
					provider.output(model);
				}
			}
		}
	}
	
	public List<InformOutputProvider> getOutputProviders() {
		return outputProviders;
	}
	
}
