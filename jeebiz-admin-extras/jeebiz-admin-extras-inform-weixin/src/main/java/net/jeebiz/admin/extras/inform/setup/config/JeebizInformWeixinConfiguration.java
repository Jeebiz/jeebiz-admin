/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import me.chanjar.weixin.mp.api.WxMpService;
import net.jeebiz.admin.extras.inform.weixin.InformWxMaOutputProvider;
import net.jeebiz.admin.extras.inform.weixin.InformWxMpOutputProvider;

@Configuration
public class JeebizInformWeixinConfiguration {
	
	@Bean
	@ConditionalOnBean(WxMaService.class)
	public InformWxMaOutputProvider informWxMaOutputProvider(WxMaService wxMaService) {
		return new InformWxMaOutputProvider(wxMaService);
	}
	
	@Bean
	@ConditionalOnBean(WxMpService.class)
	public InformWxMpOutputProvider informWxMpOutputProvider(WxMpService wxMpService) {
		return new InformWxMpOutputProvider(wxMpService);
	}

}
