/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

import io.hiwepy.boot.api.dao.entities.PairModel;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public enum InformFromType {

	EVENT("event", "消息通知事件"),
	SEND("send", "用户主动发送");

	private String key;
	private String desc;

	private InformFromType(String key, String desc) {
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

	public boolean equals(InformFromType type){
		return this.compareTo(type) == 0;
	}

	public boolean equals(String key){
		return this.compareTo(InformFromType.valueOfIgnoreCase(key)) == 0;
	}

	public static InformFromType valueOfIgnoreCase(String key) {
		for (InformFromType optType : InformFromType.values()) {
			if(optType.getKey().equalsIgnoreCase(key)) {
				return optType;
			}
		}
    	throw new NoSuchElementException("Cannot found InformFromType with key '" + key + "'.");
    }

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.getKey());
		pair.setValue(this.getDesc());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> pairList = new LinkedList<PairModel>();
		for (InformSendChannel sendChannel : InformSendChannel.values()) {
			pairList.add(sendChannel.toPair());
		}
		return pairList;
	}

}
