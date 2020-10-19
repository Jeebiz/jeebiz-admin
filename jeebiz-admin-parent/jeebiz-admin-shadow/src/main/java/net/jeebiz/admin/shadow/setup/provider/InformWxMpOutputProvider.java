/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.provider;

import org.springframework.beans.factory.annotation.Autowired;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.subscribe.WxMpSubscribeMessage;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.provider.InformOutputProvider;

public class InformWxMpOutputProvider implements InformOutputProvider<WxMpSubscribeMessage> {

	@Autowired
	private WxMpService wxMpService;

	@Override
	public InformProvider getProvider() {
		return InformProvider.WXMP;
	}

	@Override
	public boolean output(WxMpSubscribeMessage inform) {
		try {
			getWxMpService().getSubscribeMsgService().sendSubscribeMessage(inform);
			return true;
		} catch (Exception e) {
			System.out.println("推送失败：" + e.getMessage());
		}
		return false;
	}

	public WxMpService getWxMpService() {
		return wxMpService;
	}
	
}
