/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package net.jeebiz.admin.extras.inform.emums;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.slf4j.helpers.MessageFormatter;

import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.extras.redis.setup.RedisKeyConstant;

/**
 * 消息通知对象
 */
public enum InformTarget {

	/**
	 * 所有用户
	 */
	ALL("所有用户", (userId)->{
		String keyStr = MessageFormatter.format(RedisKeyConstant.USER_INFO_KEY, "zset").getMessage();
		return RedisKeyConstant.getKeyStr(keyStr);
    }),
	/**
	 * 指定用户
	 */
	SPECIFIC("指定用户", (userId)->{
		String keyStr = MessageFormatter.format(RedisKeyConstant.USER_INFO_KEY, userId).getMessage();
		return RedisKeyConstant.getKeyStr(keyStr);
    });

	private String target;
    private Function<String,String> function;

	private InformTarget(String target, Function<String,String> function) {
		this.target = target;
		this.function = function;
	}

	public String getTarget() {
		return target;
	}

	public Function<String, String> getFunction() {
        return function;
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
		throw new NoSuchElementException("Cannot found Thirdparty with key '" + key + "'.");
	}

	public PairModel toPair() {
		PairModel pair = new PairModel();
		pair.setKey(this.name());
		pair.setValue(this.getTarget());
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
