/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.goeasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.spring.boot.AbstractMessageListener;
import com.aliyun.openservices.spring.boot.annotation.MessageConsumer;

import io.goeasy.GoEasy;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.extras.inform.setup.JPushConstants;

@Slf4j
@Component
@MessageConsumer(topic = JPushConstants.JPUSH_TOPIC, tag = JPushConstants.TAG_ALL)
public class GoEasyOperationConsumer extends AbstractMessageListener {

    @Autowired
    protected RedisOperationTemplate redisOperationTemplate;
    @Autowired
	protected GoEasy goEasy;
    
    @Override
    public int apply(Message message) {
        return 0;
    }
    
    @Override
    public void consume(int count, Message message) throws Exception {
       
    	log.info("{} {} ：剩余重试次数{}", JPushConstants.JPUSH_TOPIC, JPushConstants.TAG_ALL, message.getReconsumeTimes());
 
    	//goEasy.publish(channel, content);
    	
    }


}
