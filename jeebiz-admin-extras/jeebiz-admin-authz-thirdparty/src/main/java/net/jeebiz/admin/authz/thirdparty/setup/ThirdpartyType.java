/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.thirdparty.setup;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.redis.core.RedisKey;

import net.jeebiz.admin.extras.redis.setup.BizRedisKeyConstant;

/**
 * 第三方账号类型
 */
public enum ThirdpartyType {
	
	/**
	 * 微信小程序
	 */
	WXMA("微信小程序", (userId, userType) ->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKey.getKeyStr(keyStr, "00");
    }),
	/**
	 * 微信小程序
	 */
	WXMP("微信公众号", (userId, userType) ->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKey.getKeyStr(keyStr, "00");
    }),
	/**
	 * 腾讯QQ
	 */
	QQ("腾讯QQ", (userId, userType) ->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKey.getKeyStr(keyStr, "00");
    }),
	/**
	 * 微信小程序
	 */
	WEIBO("新浪微博", (userId, userType) ->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKey.getKeyStr(keyStr, "00");
    }),
	/**
	 * 易班
	 */
	YIBAN("易班", (userId, userType) ->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKey.getKeyStr(keyStr, "00");
    });

	private String vendor;
    private BiFunction<String, String, String> function;
    
	private ThirdpartyType(String vendor, BiFunction<String, String, String> function) {
		this.vendor = vendor;
        this.function = function;
	}

	public String getVendor() {
		return vendor;
	}
	
	public BiFunction<String, String, String> getFunction() {
		return function;
	}

	public boolean equals(ThirdpartyType type) {
		return this.compareTo(type) == 0;
	}

	public boolean equals(String type) {
		return this.compareTo(ThirdpartyType.valueOfIgnoreCase(type)) == 0;
	}

	public static ThirdpartyType valueOfIgnoreCase(String key) {
		for (ThirdpartyType thirdpartyEnum : ThirdpartyType.values()) {
			if (thirdpartyEnum.name().equals(key)) {
				return thirdpartyEnum;
			}
		}
		throw new NoSuchElementException("Cannot found Thirdparty with key '" + key + "'.");
	}

	public Map<String, String> toMap() {
		Map<String, String> driverMap = new HashMap<String, String>();
		driverMap.put("name", this.name());
		driverMap.put("vendor", this.getVendor());
		return driverMap;
	}

	public static List<Map<String, String>> driverList() {
		List<Map<String, String>> driverList = new LinkedList<Map<String, String>>();
		for (ThirdpartyType dbType : ThirdpartyType.values()) {
			driverList.add(dbType.toMap());
		}
		return driverList;
	}
	 
}