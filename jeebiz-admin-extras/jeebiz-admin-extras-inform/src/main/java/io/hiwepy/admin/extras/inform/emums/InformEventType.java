/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

import io.hiwepy.boot.api.dao.entities.PairModel;

import java.util.*;

public enum InformEventType {

	NOTICE("notice", "通知"),
	LETTER("direct", "私信"),
	COMMIT_DEPLOY("commit_deploy", "提交发布"),
	AUDIT_AGREE("audit_agree", "审批通过"),
	AUDIT_REFUSE("audit_refuse", "审批拒绝"),
	DEPLOY_SUCESS("deploy_sucess", "发布成功"),
	DEPLOY_FAIL("deploy_fail", "发布失败");

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
    	throw new NoSuchElementException("Cannot found AuthzOptEnum with key '" + key + "'.");
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
