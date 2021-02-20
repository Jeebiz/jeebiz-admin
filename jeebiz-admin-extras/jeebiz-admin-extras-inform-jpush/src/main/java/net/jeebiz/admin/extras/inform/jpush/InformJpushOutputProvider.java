/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.jpush;

import java.util.Arrays;

import cn.jpush.spring.boot.JPushTemplate;
import cn.jpush.spring.boot.PushObject;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.JPushConstants;
import net.jeebiz.admin.extras.inform.setup.provider.InformOutputProvider;
import net.jeebiz.admin.extras.inform.web.dto.InformRecordDTO;

public class InformJpushOutputProvider implements InformOutputProvider<InformRecordDTO> {

	protected JPushTemplate jPushTemplate;
	
	public InformJpushOutputProvider(JPushTemplate jPushTemplate) {
		super();
		this.jPushTemplate = jPushTemplate;
	}

	@Override
	public InformProvider getProvider() {
		return InformProvider.JPUSH;
	}
	
	@Override
	public boolean output(InformRecordDTO informDTO) {
			
		PushObject pushObject = new PushObject();
		pushObject.setMsgContent(informDTO.getContent());

		jPushTemplate.sendPush(Arrays.asList(JPushConstants.PUSH_PREFIX + informDTO.getUid()),pushObject);
		
		return false;
	}
	
}
