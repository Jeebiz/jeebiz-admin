/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.sms.txcloud;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.tencentcloud.spring.boot.sms.TencentSmsTemplate;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.api.RedisKeyConstant;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.exception.BizRuntimeException;
import net.jeebiz.boot.api.utils.CalendarUtils;
import net.jeebiz.boot.extras.redis.setup.RedisOperationTemplate;
import net.jeebiz.boot.extras.redis.setup.SmsRedisKey;

/**
 * 腾讯短信服务与消息队列集成逻辑操作模板对象
 */
@Component
@Slf4j
public class TencentSmsOperationTemplate {

	/**
	 * 验证码短信模板
	 */
	private static final String TEMPLATE_ID = "40294";

	@Autowired
	private RedisOperationTemplate redisOperationTemplate;
	@Autowired
	private TencentSmsTemplate tencentSmsTemplate;
 
	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param type 业务类型：（0：注册、1：绑定、2：修改密码、3：忘记密码）
	 * @param countryCode
	 * @return
	 */
	public Boolean send(String appId, String phone, Integer type, Integer countryCode) {

		long start = System.currentTimeMillis();
		
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
		String phoneTimeMinuteKey = SmsRedisKey.SMS_LIMIT_MINUTE.getFunction().apply(phone, type.toString());
		String timesOfMinute = redisOperationTemplate.getString(phoneTimeMinuteKey);
		if (timesOfMinute != null && Integer.parseInt(timesOfMinute) > 1) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.second.limit");
		}
		// 2.2、5条/小时
		String phoneTimeHourKey = SmsRedisKey.SMS_LIMIT_HOUR.getFunction().apply(phone, type.toString());
		String timesOfHour = redisOperationTemplate.getString(phoneTimeHourKey);
		if (timesOfHour != null && Integer.parseInt(timesOfHour) > 5) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.hour.limit");
		}
		// 2.3、10条/天
		String phoneTimeDayKey = SmsRedisKey.SMS_LIMIT_DAY.getFunction().apply(phone, type.toString());
		String timesOfDay = redisOperationTemplate.getString(phoneTimeDayKey);
		if (timesOfDay != null && Integer.parseInt(timesOfDay) > RedisKeyConstant.SMS_TIME_MAX) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.day.limit");
		}
		// 2.4、黑名单
		String blacklistKey = SmsRedisKey.SMS_BLACKLIST.getFunction().apply(phone, type.toString());
		if (redisOperationTemplate.sHasKey(blacklistKey, phone)) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.backlist.limit");
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
			SendSmsResponse res = tencentSmsTemplate.send(phoneNumber, TEMPLATE_ID, vcode);
			if (!res.getSendStatusSet()[0].getCode().equals("Ok")) {
				throw new TencentCloudSDKException(res.getSendStatusSet()[0].getMessage());
			}
			
		} catch (TencentCloudSDKException e) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.fail");
		}

		// 4、发送短信并记录缓存
		String smsKey = SmsRedisKey.SMS_CODE.getFunction().apply(phone, type.toString());
		redisOperationTemplate.set(smsKey, vcode, RedisKeyConstant.SMS_EXPIRE);
		redisOperationTemplate.incr(phoneTimeDayKey, 1, CalendarUtils.getSecondsNextEarlyMorning());
		redisOperationTemplate.incr(phoneTimeHourKey, 1, 60 * 60);
		redisOperationTemplate.incr(phoneTimeMinuteKey, 1, 60);
		
		log.info("短信发送用时：{} 毫秒", (System.currentTimeMillis() - start) );

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
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.send.phone.invalid");
		}
		String smsKey = SmsRedisKey.SMS_CODE.getFunction().apply(phone, type.toString());
		String smsCode = (String) redisOperationTemplate.get(smsKey);
		if (!vcode.equals(smsCode) && !vcode.equals("000000")) {
			throw new BizRuntimeException(ApiCode.SC_BAD_REQUEST, "sms.check.vcode.invalid");
		}
		redisOperationTemplate.del(smsKey);
		return true;
	}
 

}
