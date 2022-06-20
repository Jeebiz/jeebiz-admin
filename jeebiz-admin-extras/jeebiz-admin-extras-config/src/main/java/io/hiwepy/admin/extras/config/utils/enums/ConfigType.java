/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.config.utils.enums;

import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.admin.extras.redis.setup.BizRedisKeyConstant;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.redis.core.RedisKey;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public enum ConfigType {

	/**
	 * 钉钉配置
	 */
	DINGTALK("dingtalk", "钉钉配置", (configType, itemType)->{
		return BizRedisKey.THIRD_CONFIG.getKey(configType, itemType);
	}),
	/**
	 * 微信配置
	 */
	WEXIN("wexin", "微信配置", (configType, itemType)->{
		return BizRedisKey.THIRD_CONFIG.getKey(configType, itemType);
	}),
	/**
	 * 短信配置
	 */
	SMS("sms","短信配置",  (configType, itemType)->{
		return BizRedisKey.THIRD_CONFIG.getKey(configType, itemType);
	});

	private String key;
	private String desc;
	private BiFunction<Object, Object, String> function;

	ConfigType(String key, String desc, BiFunction<Object, Object, String> function) {
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
	
	public boolean equals(ConfigType type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String key){
		return this.compareTo(ConfigType.valueOfIgnoreCase(key)) == 0;
	}
	
	public static ConfigType valueOfIgnoreCase(String key) {
		for (ConfigType optType : ConfigType.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
    }
	
	public static List<Map<String, String>> toList() {
		List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
		for (ConfigType optEnum : ConfigType.values()) {
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
