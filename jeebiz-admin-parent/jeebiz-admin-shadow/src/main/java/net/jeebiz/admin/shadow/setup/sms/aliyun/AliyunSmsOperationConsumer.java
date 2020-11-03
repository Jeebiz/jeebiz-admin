/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.sms.aliyun;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.DateUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.spring.boot.AbstractMessageListener;
import com.aliyun.openservices.spring.boot.annotation.MessageConsumer;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.tencentcloud.spring.boot.sms.TencentSmsTemplate;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.core.setup.redis.RedisConstant;
import net.jeebiz.admin.extras.core.setup.redis.RedisKeyGenerator;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.shadow.setup.Constants;
import net.jeebiz.boot.api.exception.BizRuntimeException;

@Slf4j
@Component
@MessageConsumer(topic = Constants.SMS_TOPIC, tag = Constants.TAG_ALL)
public class AliyunSmsOperationConsumer extends AbstractMessageListener {

    @Autowired
    private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private TencentSmsTemplate tencentSmsTemplate;
	
    @Override
    public int apply(Message message) {
        return 0;
    }
    
    @Override
    public void consume(int count, Message message) throws Exception {
    	
    	long start = System.currentTimeMillis();
		
        log.info("{} {} ：剩余重试次数{}", Constants.SMS_TOPIC, Constants.TAG_ALL, message.getReconsumeTimes());
        
        JSONObject payload = JSONObject.parseObject(message.getBody(), JSONObject.class);
        
        String phone = MapUtils.getString(payload, Constants.SMS_MOBILE); 
        Integer type = MapUtils.getInteger(payload, Constants.SMS_TYPE);
        Integer countryCode = MapUtils.getInteger(payload, Constants.SMS_COUNTRYCODE);
		
        // 1、验证手机号的正确性
 		PhoneNumber swissMobileNumber = new PhoneNumber().setCountryCode(countryCode)
 				.setNationalNumber(Long.parseLong(phone));
 		boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber);
 		if (!validNumberForRegion) {
 			throw new BizRuntimeException(KlExceptionCode.SMS_PHONE_ERROR);
 		}
 		
 		// 2、生成验证码
 		String vcode = RandomStringUtils.randomNumeric(6);
 				
 		String smsKey = RedisKeyGenerator.getSmsCode(type, phone);
 		String phoneTimeKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd"), type, phone);
 		String key = redisOperationTemplate.getKey(phoneTimeKey);
 		if (key != null && Integer.parseInt(key) > RedisConstant.SMS_TIME_MAX) {
 			throw new BizRuntimeException(SMS_PHONE_MAX_ERROR);
 		}
 		
 		if (redisOperationTemplate.sHasKey(RedisConstant.SET_SMS_BLACK_LIST, phone)) {
 			throw new BizRuntimeException(SMS_PHONE_ERROR);
 		}
 		
 		try {

 			/*
 			 * 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222， 其中前面有一个+号
 			 * ，86为国家码，13711112222为手机号，最多不要超过200个手机号
 			 */
 			String phoneNumber = "+".concat(String.valueOf(countryCode)).concat(phone);
 			/* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
 			String templateID = "399803";
 			/* 模板参数: 若无模板参数，则设置为空 */
 			String[] templateParams = { vcode, "5" };
 			/*
 			 * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
 			 * 类的实例，与请求对象对应
 			 */
 			SendSmsResponse res = tencentSmsTemplate.send(phoneNumber, templateID, templateParams);

 			if (!res.getSendStatusSet()[0].getCode().equals("Ok")) {
 				throw new TencentCloudSDKException(res.getSendStatusSet()[0].getMessage());
 			}
 		} catch (TencentCloudSDKException e) {
 			e.printStackTrace();
 			throw new BizRuntimeException(SMS_ERROR);
 		}

 		// 3、发送短信并记录缓存
 		redisOperationTemplate.set(smsKey, vcode, RedisConstant.SMS_EXPIRE);
 		redisOperationTemplate.incr(phoneTimeKey, 1);
 		
 		log.info("短信发送用时：{} 毫秒", (System.currentTimeMillis() - start) );

		 
    }


}
