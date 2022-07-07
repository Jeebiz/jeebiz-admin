/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

import io.hiwepy.boot.api.dao.entities.PairModel;

import java.util.LinkedList;
import java.util.List;

public enum InformType {

	/**
	 * 文本类型
	 */
	text("文本类型"),
	/**
	 * 链接类型
	 */
	link("链接类型"),
	/**
	 * MarkDown类型
	 */
	markdown("MarkDown类型"),
	/**
	 * 跳转卡片类型
	 */
	actionCard("跳转卡片类型"),
	/**
	 * 消息卡片类型
	 */
	feedCard("消息卡片类型");

	private String desc;

	InformType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public boolean equals(InformType type){
		return this.compareTo(type) == 0;
	}

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.name());
		pair.setValue(this.getDesc());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> pairList = new LinkedList<PairModel>();
		for (InformType type : InformType.values()) {
			pairList.add(type.toPair());
		}
		return pairList;
	}

}
