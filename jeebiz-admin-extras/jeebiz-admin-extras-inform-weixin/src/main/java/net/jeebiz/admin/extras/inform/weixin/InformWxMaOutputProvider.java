/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.weixin;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import net.jeebiz.admin.extras.inform.setup.InformProvider;
import net.jeebiz.admin.extras.inform.setup.provider.InformOutputProvider;

public class InformWxMaOutputProvider implements InformOutputProvider<WxMaSubscribeMessage> {

    private WxMaService wxMaService;
	
	public InformWxMaOutputProvider(WxMaService wxMaService) {
		super();
		this.wxMaService = wxMaService;
	}

	@Override
	public InformProvider getProvider() {
		return InformProvider.WXMA;
	}

  /**
   * 
   * @param inform 订阅消息.
   * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
   * @return
   */
	@Override
	public boolean output(WxMaSubscribeMessage inform) {
		try {
			getWxMaService().getMsgService().sendSubscribeMsg(inform);
			return true;
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public WxMaService getWxMaService() {
		return wxMaService;
	}
  
	
}
