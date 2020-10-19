/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.setup.provider;

import net.jeebiz.admin.extras.inform.setup.InformProvider;

public interface InformOutputProvider<T> {

	/**
	 * Inform Provider
	 * @return
	 */
	InformProvider getProvider();
	
	/**
	 *消息发送
	 * @param inform 消息通知对象
	 * @return 发送成功识别
	 */
	boolean output(T inform);
	
}
