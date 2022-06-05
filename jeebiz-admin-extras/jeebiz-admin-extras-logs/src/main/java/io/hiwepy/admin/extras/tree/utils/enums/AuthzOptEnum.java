/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.tree.utils.enums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public enum AuthzOptEnum {
	
	LOGIN("login", "登录认证"),
	LOGOUT("logout", "会话注销");
	
	private String key;
	private String desc;

	private AuthzOptEnum(String key, String desc) {
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
	
	public boolean equals(AuthzOptEnum type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String key){
		return this.compareTo(AuthzOptEnum.valueOfIgnoreCase(key)) == 0;
	}
	
	public static AuthzOptEnum valueOfIgnoreCase(String key) {
		for (AuthzOptEnum optType : AuthzOptEnum.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
    }
	
	public static List<Map<String, String>> toList() {
		List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
		for (AuthzOptEnum optEnum : AuthzOptEnum.values()) {
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
