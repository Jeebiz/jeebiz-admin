/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.logbiz.utils.enums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 异常来源
 */
public enum ExceptionSourceEnum {
	
	CONTROLLER("Controller", "控制器"),
	ADAPTER("Adapter", "适配器"),
	MAVEN("Maven", "Maven"),
	RABBITMQ("RabbitMQ", "RabbitMQ"),
	SERVICE("Service", "服务器");
	
	private String key;
	private String desc;

	private ExceptionSourceEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
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
	
	public boolean equals(ExceptionSourceEnum type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String key){
		return this.compareTo(ExceptionSourceEnum.valueOfIgnoreCase(key)) == 0;
	}
	
	public static ExceptionSourceEnum valueOfIgnoreCase(String key) {
		for (ExceptionSourceEnum bizApiType : ExceptionSourceEnum.values()) {
			if(bizApiType.getKey().equalsIgnoreCase(key)) {
				return bizApiType;
			}
		}
    	throw new NoSuchElementException("Cannot found ExceptionTypeEnum with key '" + key + "'.");
    }
	
	public static List<Map<String, String>> toList() {
		List<Map<String, String>> componentList = new LinkedList<Map<String, String>>();
		for (ExceptionSourceEnum omponentEnum : ExceptionSourceEnum.values()) {
			componentList.add(omponentEnum.toMap());
		}
		return componentList;
	}

	public  Map<String, String> toMap() {
		Map<String, String> omponentMap = new HashMap<String, String>();
		omponentMap.put("key", this.getKey());
		omponentMap.put("desc", this.getDesc());
		return omponentMap;
	}
	
}
