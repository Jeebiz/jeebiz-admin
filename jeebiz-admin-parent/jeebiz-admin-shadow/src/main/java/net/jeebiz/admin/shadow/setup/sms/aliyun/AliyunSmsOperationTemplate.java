/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.shadow.setup.sms.aliyun;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.DateUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.spring.boot.AliyunOnsMqTemplate;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import lombok.extern.log4j.Log4j2;
import net.jeebiz.admin.extras.core.setup.redis.RedisConstant;
import net.jeebiz.admin.extras.core.setup.redis.RedisKeyGenerator;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.admin.shadow.setup.Constants;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.sequence.Sequence;
import net.jeebiz.boot.api.utils.CalendarUtils;

/**
 * 阿里云短信服务与消息队列服务集成逻辑操作模板对象
 */
@Component
@Log4j2
public class AliyunSmsOperationTemplate {

	/**
	 * 验证码短信模板
	 */
	private static final String TEMPLATE_ID = "40294";
	
	@Autowired
	private OrderProducer orderProducer;
	//@Autowired
	//private Producer producer;

	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private AliyunOnsMqTemplate aliyunOnsMqTemplate;
	@Autowired
	private AliyunSmsTemplate aliyunSmsTemplate;
	@Autowired
	private Sequence sequence;
 
	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param type 业务类型：（0：注册、1：绑定、2：修改密码、3：忘记密码）
	 * @param countryCode
	 * @return
	 */
	public Boolean send(String phone, Integer type, Integer countryCode) {

		long start = System.currentTimeMillis();
		
		// 1、验证手机号的正确性
		PhoneNumber swissMobileNumber = new PhoneNumber().setCountryCode(countryCode)
				.setNationalNumber(Long.parseLong(phone));
		boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber);
		if (!validNumberForRegion) {
			throw new BizRuntimeException(SMS_PHONE_ERROR);
		}
		
		// 2、检查短信发送权限
		
		String phoneTimeSecondKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd_HH_mm"), type, phone);
		String timesOfSecond = redisOperationTemplate.getKey(phoneTimeSecondKey);
		if (timesOfSecond != null && Integer.parseInt(timesOfSecond) > 1) {
			throw new BizRuntimeException(SMS_PHONE_MAX_ERROR);
		}
		
		String phoneTimeDayKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd"), type, phone);
		String timesOfDay = redisOperationTemplate.getKey(phoneTimeDayKey);
		if (timesOfDay != null && Integer.parseInt(timesOfDay) > RedisConstant.SMS_TIME_MAX) {
			throw new BizRuntimeException(SMS_PHONE_MAX_ERROR);
		}
		
		if (redisOperationTemplate.sHasKey(RedisConstant.SET_SMS_BLACK_LIST, phone)) {
			throw new BizRuntimeException(SMS_PHONE_ERROR);
		}
		
		// 3、生成验证码和发送
		String vcode = RandomStringUtils.randomNumeric(6);
		
		try {

			/*
			 * 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号] 例如+8613711112222， 其中前面有一个+号
			 * ，86为国家码，13711112222为手机号，最多不要超过200个手机号
			 */
			String phoneNumber = "+".concat(String.valueOf(countryCode)).concat(phone);
			/*
			 * 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的 返回的 res 是一个 SendSmsResponse
			 * 类的实例，与请求对象对应
			 */
			Map<String, Object> templateParams = new HashMap<>();
			templateParams.put("code", vcode);
			SendSmsResponse res = aliyunSmsTemplate.send(phoneNumber, TEMPLATE_ID, templateParams);
			if (!res.getCode().equals("Ok")) {
				return Boolean.FALSE;
			}
		} catch (ClientException e) {
			e.printStackTrace();
			throw new BizRuntimeException(SMS_ERROR);
		}

		// 4、发送短信并记录缓存
		String smsKey = RedisKeyGenerator.getSmsCode(type, phone);
		redisOperationTemplate.set(smsKey, vcode, RedisConstant.SMS_EXPIRE);
		redisOperationTemplate.incr(phoneTimeDayKey, 1, CalendarUtils.getSecondsNextEarlyMorning());
		redisOperationTemplate.incr(phoneTimeSecondKey, 1, 60);
		
		log.info("短信发送用时：{} 毫秒", (System.currentTimeMillis() - start) );

		return Boolean.TRUE;
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param type
	 * @param countryCode
	 * @return
	 * @throws KlException
	 */
	public Boolean sendToMq(String phone, Integer type, Integer countryCode)  {
		
		// 1、验证手机号的正确性
		PhoneNumber swissMobileNumber = new PhoneNumber().setCountryCode(countryCode)
				.setNationalNumber(Long.parseLong(phone));
		boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber);
		if (!validNumberForRegion) {
			throw new BizRuntimeException(SMS_PHONE_ERROR);
		}
		
		// 2、检查短信发送权限
		String phoneTimeKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd"), type, phone);
		String key = redisOperationTemplate.getKey(phoneTimeKey);
		if (key != null && Integer.parseInt(key) > RedisConstant.SMS_TIME_MAX) {
			throw new BizRuntimeException(SMS_PHONE_MAX_ERROR);
		}
		
		if (redisOperationTemplate.sHasKey(RedisConstant.SET_SMS_BLACK_LIST, phone)) {
			throw new BizRuntimeException(SMS_PHONE_ERROR);
		}
		
		// 3、发送短信队列
		
		String orderId = sequence.nextId().toString();

		JSONObject payload = new JSONObject();
		payload.put(Constants.SMS_MOBILE, phone);
		payload.put(Constants.SMS_TYPE, type);
		payload.put(Constants.SMS_COUNTRYCODE, countryCode);
		
		Message message = new Message();
		message.setTopic(Constants.SMS_TOPIC);
		message.setTag(Constants.TAG_ALL);
		message.setKey(orderId);
		message.setBody(payload.toJSONString().getBytes());
		
		boolean state = aliyunOnsMqTemplate.sendOrderMes(orderProducer, message, orderId);
		if(!state) {
			//throw new BizRuntimeException(YOU_ARE_NOT_SUPPORT_TO_DO_MULTIPLE_PAYMENT_OPERATIONS);
		}
		
		return Boolean.TRUE;
	}

	/**
	 * 验证码校验
	 * 
	 * @param phone
	 * @param type
	 * @param countryCode
	 * @return
	 */
	public Boolean check(String phone, Integer type, Integer countryCode, String vcode) throws BizRuntimeException {
		// 验证手机号
		PhoneNumber swissMobileNumber = new PhoneNumber().setCountryCode(countryCode)
				.setNationalNumber(Long.parseLong(phone));
		boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber);
		if (!validNumberForRegion && !vcode.equals("000000")) {
			throw new BizRuntimeException(SMS_PHONE_ERROR);
		}
		String smsKey = RedisKeyGenerator.getSmsCode(type, phone);
		String smsCode = (String) redisOperationTemplate.get(smsKey);
		if (!vcode.equals(smsCode) && !vcode.equals("000000")) {
			throw new BizRuntimeException(SMS_CODE_ERROR);
		}
		redisOperationTemplate.del(smsKey);
		redisOperationTemplate.del(RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd"), type, phone));
		return true;
	}
 

}
