/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.aliyun;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.DateUtils;
import org.springframework.stereotype.Component;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.extras.core.setup.redis.RedisConstant;
import net.jeebiz.admin.extras.core.setup.redis.RedisKeyGenerator;
import net.jeebiz.admin.extras.core.setup.redis.RedisOperationTemplate;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CalendarUtils;

/**
 * 阿里云短信服务与消息队列服务集成逻辑操作模板对象
 */
@Component
@Slf4j
public class AliyunSmsOperationTemplate {

	protected static final String CODE_OK = "OK";
	protected static final String CODE_BUSINESS_LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";
	
	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private AliyunSmsTemplate aliyunSmsTemplate;
	
	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param type 业务类型：（0：注册、1：绑定、2：修改密码、3：忘记密码，4：手机号码登录）
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
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.phone.invalid");
		}
		
		// 2、检查短信发送权限
		
		// 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。
		// 短信通知： 使用同一个签名和同一个短信模板id，对同一个手机号码发送短信通知，支持50条/日
		
		// 2.1、1条/分钟
		String phoneTimeSecondKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd_HH_mm"), type, phone);
		String timesOfSecond = redisOperationTemplate.getString(phoneTimeSecondKey);
		if (timesOfSecond != null && Integer.parseInt(timesOfSecond) > 1) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.second.limit");
		}
		// 2.2、5条/小时
		String phoneTimeHourKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd_HH"), type, phone);
		String timesOfHour = redisOperationTemplate.getString(phoneTimeHourKey);
		if (timesOfHour != null && Integer.parseInt(timesOfHour) > 5) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.hour.limit");
		}
		// 2.3、10条/天
		String phoneTimeDayKey = RedisKeyGenerator.getSmsMobileTime(DateUtils.getDate("yyyy_MM_dd"), type, phone);
		String timesOfDay = redisOperationTemplate.getString(phoneTimeDayKey);
		if (timesOfDay != null && Integer.parseInt(timesOfDay) > RedisConstant.SMS_TIME_MAX) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.day.limit");
		}
		// 2.4、黑名单
		if (redisOperationTemplate.sHasKey(RedisConstant.SET_SMS_BLACK_LIST, phone)) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.backlist.limit");
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
			/**
			 * 验证码短信模板
			 */
			String templateCode = "SMS_203740130";
			switch (type) {
				case 0: 
				case 1: {
					templateCode ="SMS_203740130"; // 用户注册验证码
				};break; 
				case 2:
				case 3: {
					templateCode ="SMS_203740129"; // 修改密码验证码
				};break; 
				case 4: { 
					templateCode ="SMS_203740132"; // 登录确认验证码
				};break; 
				default:{};break;
			}
			
			SendSmsResponse res = aliyunSmsTemplate.send(phoneNumber, templateCode, templateParams);
			if(StringUtils.equalsIgnoreCase(res.getCode(), CODE_BUSINESS_LIMIT_CONTROL)) {
				throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.max.limit");
			}
			if (!res.getCode().equals(CODE_OK)) {
				return Boolean.FALSE.booleanValue();
			}
		} catch (ClientException e) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.fail");
		}

		// 4、发送短信并记录缓存
		String smsKey = RedisKeyGenerator.getSmsCode(type, phone);
		redisOperationTemplate.set(smsKey, vcode, RedisConstant.SMS_EXPIRE);
		redisOperationTemplate.incr(phoneTimeDayKey, 1, CalendarUtils.getSecondsNextEarlyMorning());
		redisOperationTemplate.incr(phoneTimeHourKey, 1, 60 * 60);
		redisOperationTemplate.incr(phoneTimeSecondKey, 1, 60);
		
		log.info("短信发送用时：{} 毫秒", (System.currentTimeMillis() - start) );

		return Boolean.TRUE.booleanValue();
	}
	
	/**
	 * 验证码校验
	 * 
	 * @param phone
	 * @param type
	 * @param countryCode
	 * @return
	 */
	public Boolean check(String phone, Integer type, Integer countryCode, String vcode) {
		// 验证手机号
		PhoneNumber swissMobileNumber = new PhoneNumber().setCountryCode(countryCode)
				.setNationalNumber(Long.parseLong(phone));
		boolean validNumberForRegion = PhoneNumberUtil.getInstance().isValidNumber(swissMobileNumber);
		if (!validNumberForRegion && !vcode.equals("000000")) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.send.phone.invalid");
		}
		String smsKey = RedisKeyGenerator.getSmsCode(type, phone);
		String smsCode = (String) redisOperationTemplate.get(smsKey);
		if (!vcode.equals(smsCode) && !vcode.equals("000000")) {
			throw new BizRuntimeException(ApiCode.SC_FAIL, "sms.check.vcode.invalid");
		}
		redisOperationTemplate.del(smsKey);
		return true;
	}
 

}
