/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.jpush;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.spring.boot.AliyunOnsMqTemplate;

import cn.jpush.spring.boot.JPushTemplate;
import cn.jpush.spring.boot.PushObject;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.shadow.setup.Constants;
import net.jeebiz.boot.api.sequence.Sequence;

/**
 *   极光推送与消息队列集成逻辑操作模板对象
 */
@Component
public class JPushOperationTemplate {

	@Autowired
	protected RedisOperationTemplate redisOperationTemplate;
	@Autowired
	protected OrderProducer orderProducer;
	@Autowired
	protected AliyunOnsMqTemplate aliyunOnsMqTemplate;
	@Autowired
	protected Sequence sequence;
	@Autowired
	protected JPushTemplate jPushTemplate;
	

	/**
	 * 1、极光推送处理逻辑（直接调用客户端发送）
	 * @param uid		：用户ID
	 */
	public void send(String uid, String tag, PushObject pushObject) {
		List<String> uids = new ArrayList<>();
		uids.add(uid);
		List<String> tags = new ArrayList<>();
		tags.add(tag);
		jPushTemplate.sendPush(pushObject);

		jPushTemplate.sendPush(uids,pushObject);

		jPushTemplate.sendPushByTag(tags,pushObject);
	}

	public void sendWithUid(String uid, String content){
		PushObject pushObject = new PushObject();
		pushObject.setMsgContent(content);
		jPushTemplate.sendPush(Arrays.asList(Constants.PUSH_PREFIX + uid),pushObject);
	}
	
	/**
	 * 2、极光推送处理逻辑（先发送到消息队列，通过消息队列进行逻辑处理）
	 * @param uid		：用户ID
	 * @param push		：推送内容
	 */
	public void sendToMq(Integer uid, PushObject push) {
		
		String orderId = sequence.nextId().toString();

		Message message = new Message();
		message.setTopic(Constants.JPUSH_TOPIC);
		message.setTag(Constants.TAG_ALL);
		message.setKey(orderId);
		message.setBody(JSON.toJSONString(push).getBytes());
		
		boolean state = aliyunOnsMqTemplate.sendOrderMes(orderProducer, message, orderId);
		if(!state) {
			//throw new KlRuntimeException(KlExceptionCode.YOU_ARE_NOT_SUPPORT_TO_DO_MULTIPLE_PAYMENT_OPERATIONS);
		}
	}

}
