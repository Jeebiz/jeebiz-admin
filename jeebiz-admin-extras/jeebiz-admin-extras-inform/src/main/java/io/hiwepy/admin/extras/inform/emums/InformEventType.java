/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

import io.hiwepy.boot.api.dao.entities.PairModel;

import java.util.*;

public enum InformEventType {

	LOGIN("login", "系统登录"),
	LOGOUT("logout", "注销登录");

	private String key;
	private String desc;

	private InformEventType(String key, String desc) {
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

	public boolean equals(InformEventType type){
		return this.compareTo(type) == 0;
	}

	public boolean equals(String key){
		return this.compareTo(InformEventType.valueOfIgnoreCase(key)) == 0;
	}

	public static InformEventType valueOfIgnoreCase(String key) {
		for (InformEventType optType : InformEventType.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found InformEventType with key '" + key + "'.");
    }

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.getKey());
		pair.setValue(this.getDesc());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> pairList = new LinkedList<PairModel>();
		for (InformSendChannel type : InformSendChannel.values()) {
			pairList.add(type.toPair());
		}
		return pairList;
	}

}
