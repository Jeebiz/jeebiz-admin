/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.goeasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.spring.boot.AliyunOnsMqTemplate;

import io.goeasy.GoEasy;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.boot.api.sequence.Sequence;

/**
 *   极光推送与消息队列集成逻辑操作模板对象
 */
@Component
public class GoEasyOperationTemplate {

	@Autowired
	protected RedisOperationTemplate redisOperationTemplate;
	@Autowired
	protected OrderProducer orderProducer;
	@Autowired
	protected AliyunOnsMqTemplate aliyunOnsMqTemplate;
	@Autowired
	protected Sequence sequence;
	@Autowired
	protected GoEasy goEasy;
	
 

}
