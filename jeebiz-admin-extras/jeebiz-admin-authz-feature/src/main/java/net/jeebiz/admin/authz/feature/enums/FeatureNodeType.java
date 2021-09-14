/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.authz.feature.enums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 *  节点类型（tree:树形结构,flat:扁平结构）
 */
public enum FeatureNodeType {
	
	TREE("tree", "树形结构"),
	FLAT("flat", "扁平结构"),
	;

	private String key;
	private String desc;

	private FeatureNodeType(String key, String desc) {
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
	
	public boolean equals(FeatureNodeType type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String key){
		return this.compareTo(FeatureNodeType.valueOfIgnoreCase(key)) == 0;
	}
	
	public static FeatureNodeType valueOfIgnoreCase(String key) {
		for (FeatureNodeType optType : FeatureNodeType.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
    }
	
	public static List<Map<String, String>> toList() {
		List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
		for (FeatureNodeType optEnum : FeatureNodeType.values()) {
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
