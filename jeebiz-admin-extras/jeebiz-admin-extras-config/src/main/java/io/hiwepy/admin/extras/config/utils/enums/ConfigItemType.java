/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.utils.enums;

import io.hiwepy.admin.extras.redis.setup.BizRedisKeyConstant;
import org.springframework.data.redis.core.RedisKey;

import java.util.*;
import java.util.function.BiFunction;

public enum ConfigItemType {

	/**
	 * 企业内部开发：小程序、H5配置
	 */
	DINGTALK_INTERNAL("dingtalk-internal", "企业内部开发：小程序、H5配置", (configType, itemType)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.CONFIG_KEY, configType, itemType);
	}),
	/**
	 * 第三方个人应用：小程序配置
	 */
	DINGTALK_PERSONAL("dingtalk-personal", "第三方个人应用：小程序配置", (configType, itemType)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.CONFIG_KEY, configType, itemType);
	}),
	/**
	 * 第三方企业应用：小程序、H5配置
	 */
	DINGTALK_ENTERPRISE("dingtalk-enterprise","第三方企业应用：小程序、H5配置",  (configType, itemType)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.CONFIG_KEY, configType, itemType);
	}),
	/**
	 * 移动接入应用：扫码登录配置
	 */
	DINGTALK_LOGIN("sms","移动接入应用：扫码登录配置",  (configType, itemType)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.CONFIG_KEY, configType, itemType);
	}),
	/**
	 * 短信配置
	 */
	DINGTALK_ROBOT("sms","Dingtalk：机器人配置",  (configType, itemType)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.CONFIG_KEY, configType, itemType);
	});

	private String key;
	private String desc;
	private BiFunction<Object, Object, String> function;

	ConfigItemType(String key, String desc, BiFunction<Object, Object, String> function) {
		this.key = key;
		this.desc = desc;
		this.function = function;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean equals(ConfigItemType type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String key){
		return this.compareTo(ConfigItemType.valueOfIgnoreCase(key)) == 0;
	}
	
	public static ConfigItemType valueOfIgnoreCase(String key) {
		for (ConfigItemType optType : ConfigItemType.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
    }
	
	public static List<Map<String, String>> toList() {
		List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
		for (ConfigItemType optEnum : ConfigItemType.values()) {
			optList.add(optEnum.toMap());
		}
		return optList;
	}

	public  Map<String, String> toMap() {
		Map<String, String> optMap = new HashMap<String, String>();
		optMap.put("key", this.getKey());
		optMap.put("desc", this.getDesc());
		return optMap;
	}

}
