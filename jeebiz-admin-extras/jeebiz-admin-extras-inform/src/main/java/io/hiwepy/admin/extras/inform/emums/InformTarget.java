/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.inform.emums;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.redis.core.RedisKey;

import io.hiwepy.admin.extras.redis.setup.BizRedisKeyConstant;
import io.hiwepy.boot.api.dao.entities.PairModel;

/**
 * 消息通知对象
 */
public enum InformTarget {

	/**
	 * 所有用户
	 */
	ALL("all", "触发人", (p1, p2)->{
		return RedisKey.getKeyStr(BizRedisKeyConstant.USER_LIST_KEY);
	}),
	/**
	 * 触发人
	 */
	TRIGGER("trigger", "触发人", (nodeKey, userId)->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKey.getKeyStr(keyStr);
	}),
	/**
	 * 部分对象
	 */
	SPECIFIC("specific","部分对象",  (groupKey, userId)->{
		String keyStr = MessageFormatter.format(BizRedisKeyConstant.USER_SPECIFIC_KEY, groupKey).getMessage();
		return RedisKey.getKeyStr(keyStr);
	});

	private String key;
	private String desc;
	private BiFunction<Object, Object, String> function;

	InformTarget(String key, String desc, BiFunction<Object, Object, String> function) {
		this.key = key;
		this.desc = desc;
		this.function = function;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public boolean equals(InformTarget target) {
		return this.compareTo(target) == 0;
	}

	public boolean equals(String target) {
		return this.compareTo(InformTarget.valueOfIgnoreCase(target)) == 0;
	}

	public static InformTarget valueOfIgnoreCase(String key) {
		for (InformTarget targetEnum : InformTarget.values()) {
			if (targetEnum.name().equals(key)) {
				return targetEnum;
			}
		}
		throw new NoSuchElementException("Cannot found InformTarget with key '" + key + "'.");
	}

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.getKey());
		pair.setValue(this.getDesc());
		return pair;
	}

	public static List<PairModel> toList() {
		List<PairModel> pairList = new LinkedList<PairModel>();
		for (InformTarget targetEnum : InformTarget.values()) {
			pairList.add(targetEnum.toPair());
		}
		return pairList;
	}

}

