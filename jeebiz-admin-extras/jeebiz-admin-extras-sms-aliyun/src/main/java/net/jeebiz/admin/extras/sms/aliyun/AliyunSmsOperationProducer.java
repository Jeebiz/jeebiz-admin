/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.aliyun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.DateUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.spring.boot.AliyunOnsMqTemplate;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import net.jeebiz.admin.extras.core.setup.redis.RedisConstant;
import net.jeebiz.admin.extras.core.setup.redis.RedisKeyGenerator;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.sequence.Sequence;

/**
 * 阿里云短信服务与消息队列服务集成逻辑操作模板对象
 */
@Component
public class AliyunSmsOperationProducer {

	protected static final String CODE_OK = "OK";
	protected static final String CODE_BUSINESS_LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";
	
	@Autowired
	private OrderProducer orderProducer;
	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private AliyunOnsMqTemplate aliyunOnsMqTemplate;
	@Autowired
	private Sequence sequence;

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
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.phone.invalid");
		}
		
		// 2、检查短信发送权限

		// 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
		// 短信通知： 使用同一个签名和同一个短信模板id，对同一个手机号码发送短信通知，支持50条/日
		
		// 2.1、1条/分钟
		String phoneTimeSecondKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd_HH_mm"), type, phone);
		String timesOfSecond = redisOperationTemplate.getString(phoneTimeSecondKey);
		if (timesOfSecond != null && Integer.parseInt(timesOfSecond) > 1) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.second.limit");
		}
		// 2.2、5条/小时
		String phoneTimeHourKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd_HH"), type, phone);
		String timesOfHour = redisOperationTemplate.getString(phoneTimeHourKey);
		if (timesOfHour != null && Integer.parseInt(timesOfHour) > 5) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.hour.limit");
		}
		// 2.3、10条/天
		String phoneTimeDayKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd"), type, phone);
		String timesOfDay = redisOperationTemplate.getString(phoneTimeDayKey);
		if (timesOfDay != null && Integer.parseInt(timesOfDay) > RedisConstant.SMS_TIME_MAX) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.day.limit");
		}
		// 2.4、黑名单
		if (redisOperationTemplate.sHasKey(RedisConstant.SET_SMS_BLACK_LIST, phone)) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.backlist.limit");
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
			//throw new BizRuntimeException(KlExceptionCode.YOU_ARE_NOT_SUPPORT_TO_DO_MULTIPLE_PAYMENT_OPERATIONS);
		}
		
		return Boolean.TRUE.booleanValue();
	}

}
