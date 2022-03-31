/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.strategy;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.inform.bo.InformSendBO;
import net.jeebiz.admin.extras.inform.bo.InformSendResult;
import net.jeebiz.admin.extras.inform.emums.InformSendChannel;

@Component
@Slf4j
public class LetterInformSendStrategy extends AbstractInformSendStrategy {

	@Override
	public InformSendChannel getChannel() {
		return InformSendChannel.NOTICE;
	}

	@Override
	protected <R extends InformSendResult, O extends InformSendBO> R handleSend(O sendBo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
