/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.inform.utils.enums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public enum InformTypeEnum {
	
	NOTICE("notice", "通知"),
	LETTER("direct", "私信"),
	COMMIT_DEPLOY("commit_deploy", "提交发布"),
	AUDIT_AGREE("audit_agree", "审批通过"),
	AUDIT_REFUSE("audit_refuse", "审批拒绝"),
	DEPLOY_SUCESS("deploy_sucess", "发布成功"),
	DEPLOY_FAIL("deploy_fail", "发布失败");

	private String key;
	private String desc;

	private InformTypeEnum(String key, String desc) {
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
	
	public boolean equals(InformTypeEnum type){
		return this.compareTo(type) == 0;
	}
	
	public boolean equals(String key){
		return this.compareTo(InformTypeEnum.valueOfIgnoreCase(key)) == 0;
	}
	
	public static InformTypeEnum valueOfIgnoreCase(String key) {
		for (InformTypeEnum optType : InformTypeEnum.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
    }
	
	public static List<Map<String, String>> toList() {
		List<Map<String, String>> optList = new LinkedList<Map<String, String>>();
		for (InformTypeEnum optEnum : InformTypeEnum.values()) {
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
