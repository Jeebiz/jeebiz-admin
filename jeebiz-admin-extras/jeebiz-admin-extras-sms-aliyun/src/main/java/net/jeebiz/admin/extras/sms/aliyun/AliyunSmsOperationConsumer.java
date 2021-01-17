/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.aliyun;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.spring.boot.AbstractMessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
//@MessageConsumer(topic = Constants.SMS_TOPIC, tag = Constants.TAG_ALL)
public class AliyunSmsOperationConsumer extends AbstractMessageListener {
	
    @Autowired
    private AliyunSmsOperationTemplate aliyunSmsOperationTemplate;

    @Override
    public int apply(Message message) {
        return 0;
    }

    @Override
    public void consume(int count, Message message) throws Exception {

        log.info("{} {} ：剩余重试次数{}", Constants.SMS_TOPIC, Constants.TAG_ALL, message.getReconsumeTimes());

        JSONObject payload = JSONObject.parseObject(message.getBody(), JSONObject.class);

        String phone = MapUtils.getString(payload, Constants.SMS_MOBILE);
        Integer type = MapUtils.getInteger(payload, Constants.SMS_TYPE);
        Integer countryCode = MapUtils.getInteger(payload, Constants.SMS_COUNTRYCODE);

        getAliyunSmsOperationTemplate().send(phone, type, countryCode);

    }

    public AliyunSmsOperationTemplate getAliyunSmsOperationTemplate() {
        return aliyunSmsOperationTemplate;
    }

}
